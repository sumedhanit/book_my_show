package com.bookmyshow.ticket_booking_app.service;

import com.bookmyshow.ticket_booking_app.dto.BookingEvent;
import com.bookmyshow.ticket_booking_app.dto.BookingRequest;
import com.bookmyshow.ticket_booking_app.entity.*;
import com.bookmyshow.ticket_booking_app.repository.*;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class BookingService {

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingSeatRepository bookingSeatRepository;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private BookingEventProducer bookingEventProducer;

    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    @Transactional
    public Booking createBooking(BookingRequest request) {

        logger.info("Booking request received. UserId={}, ShowId={}, Seats={}",
                request.getUserId(), request.getShowId(), request.getSeatIds());

        List<RLock> locks = new ArrayList<>();

        try {

            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Show show = showRepository.findById(request.getShowId())
                    .orElseThrow(() -> new RuntimeException("Show not found"));

            // 1️⃣ Acquire distributed lock for each seat
            for (Long seatId : request.getSeatIds()) {

                String lockKey = "lock:show:" + request.getShowId() + ":seat:" + seatId;

                RLock lock = redissonClient.getLock(lockKey);

                logger.info("Trying to acquire lock for {}", lockKey);

                boolean locked = lock.tryLock(2, 10, TimeUnit.SECONDS);

                if (!locked) {
                    throw new RuntimeException("Seat currently being booked by another user");
                }

                logger.info("Lock acquired for {}", lockKey);

                locks.add(lock);
            }

            // 2️⃣ Fetch seats AFTER acquiring lock (fresh DB state)
//            List<ShowSeat> seats = showSeatRepository.findAllById(request.getSeatIds());
            List<ShowSeat> seats= showSeatRepository.findSeatsForUpdate(request.getSeatIds());
            if (seats.size() != request.getSeatIds().size()) {
                throw new RuntimeException("Some seats not found");
            }

            // Validate seats belong to show
            boolean invalidSeat = seats.stream()
                    .anyMatch(seat -> !seat.getShow().getShowId().equals(request.getShowId()));

            if (invalidSeat) {
                throw new RuntimeException("Some seats do not belong to this show");
            }

            // 3️⃣ Check seat availability AFTER lock
            boolean seatUnavailable = seats.stream()
                    .anyMatch(seat -> seat.getStatus() == SeatStatus.BOOKED ||
                            seat.getStatus() == SeatStatus.LOCKED);

            if (seatUnavailable) {
                throw new RuntimeException("Seat already booked or locked");
            }

            // 4️⃣ Mark seats LOCKED
            for (ShowSeat seat : seats) {
                seat.setStatus(SeatStatus.LOCKED);
            }

            showSeatRepository.saveAll(seats);

            logger.info("Seats locked successfully");

            // 5️⃣ Create booking
            logger.info("Booking started with show ID {}", show.getShowId());

            double totalPrice = show.getPrice() * seats.size();

            Booking booking = new Booking();
            booking.setUser(user);
            booking.setShow(show);
            booking.setTotalPrice(totalPrice);
            booking.setStatus("PENDING");

            booking = bookingRepository.save(booking);

            // 6️⃣ Create bookingSeat mapping
            List<BookingSeat> bookingSeats = new ArrayList<>();

            for (ShowSeat seat : seats) {

                BookingSeat bs = new BookingSeat();
                bs.setBooking(booking);
                bs.setShowSeat(seat);

                bookingSeats.add(bs);
            }

            bookingSeatRepository.saveAll(bookingSeats);

            logger.info("Booking created with ID {}", booking.getBookingId());

            // 7️⃣ Call payment service
            logger.info("Initiating payment for booking {}", booking.getBookingId());

//            boolean paymentSuccess = paymentService.processPayment(booking);
//
            BookingEvent event = new BookingEvent();

            event.setBookingId(booking.getBookingId());
            event.setUserId(request.getUserId());
            event.setShowId(request.getShowId());
            event.setSeatIds(request.getSeatIds());
            event.setAmount(booking.getTotalPrice());

            bookingEventProducer.sendBookingEvent(event);

            return booking;

        } catch (Exception e) {

            logger.error("Booking failed: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());

        } finally {

            // 🔓 Release locks
            for (RLock lock : locks) {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                    logger.info("Lock released");
                }
            }
        }
    }
}