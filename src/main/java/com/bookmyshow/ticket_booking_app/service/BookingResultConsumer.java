package com.bookmyshow.ticket_booking_app.service;

import com.bookmyshow.ticket_booking_app.dto.PaymentFailedEvent;
import com.bookmyshow.ticket_booking_app.dto.PaymentSuccessEvent;
import com.bookmyshow.ticket_booking_app.entity.Booking;
import com.bookmyshow.ticket_booking_app.entity.BookingSeat;
import com.bookmyshow.ticket_booking_app.entity.SeatStatus;
import com.bookmyshow.ticket_booking_app.entity.ShowSeat;
import com.bookmyshow.ticket_booking_app.repository.BookingRepository;
import com.bookmyshow.ticket_booking_app.repository.BookingSeatRepository;
import com.bookmyshow.ticket_booking_app.repository.ShowSeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingResultConsumer {

    private static final Logger logger =
            LoggerFactory.getLogger(BookingResultConsumer.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private BookingSeatRepository bookingSeatRepository;

    // ✅ SUCCESS
    @KafkaListener(topics = "payment-success", groupId = "booking-group")
    public void handlePaymentSuccess(PaymentSuccessEvent event){

        logger.info("Payment SUCCESS received for booking {}", event.getBookingId());

        Booking booking = bookingRepository.findById(event.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if("BOOKED".equals(booking.getStatus())){
            logger.warn("Already booked {}", booking.getBookingId());
            return;
        }

        List<ShowSeat> seats = bookingSeatRepository
                .findByBookingBookingId(booking.getBookingId())
                .stream()
                .map(BookingSeat::getShowSeat)
                .toList();

        booking.setStatus("BOOKED");

        for(ShowSeat seat : seats){
            seat.setStatus(SeatStatus.BOOKED);
        }

        showSeatRepository.saveAll(seats);
        bookingRepository.save(booking);
    }

    // ❌ FAILED
    @KafkaListener(topics = "payment-failed", groupId = "booking-group")
    public void handlePaymentFailed(PaymentFailedEvent event){

        logger.info("Payment FAILED received for booking {}", event.getBookingId());

        Booking booking = bookingRepository.findById(event.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if("FAILED".equals(booking.getStatus())){
            logger.warn("Already failed {}", booking.getBookingId());
            return;
        }

        List<ShowSeat> seats = bookingSeatRepository
                .findByBookingBookingId(booking.getBookingId())
                .stream()
                .map(BookingSeat::getShowSeat)
                .toList();

        booking.setStatus("FAILED");

        for(ShowSeat seat : seats){
            seat.setStatus(SeatStatus.AVAILABLE);
        }

        showSeatRepository.saveAll(seats);
        bookingRepository.save(booking);
    }
}
