package com.bookmyshow.ticket_booking_app.service;

import com.bookmyshow.ticket_booking_app.dto.BookingEvent;
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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingEventConsumer {

    private static final Logger logger =
            LoggerFactory.getLogger(BookingEventConsumer.class);

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private BookingSeatRepository bookingSeatRepository;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    @KafkaListener(topics = "booking-created", groupId = "booking-group")
    public void consumeBookingEvent(BookingEvent event){

        logger.info("Received booking event {}", event.getBookingId());

        Booking booking = bookingRepository.findById(event.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if("BOOKED".equals(booking.getStatus()) || "FAILED".equals(booking.getStatus())){
            logger.warn("Booking already processed {}", booking.getBookingId());
            return;
        }
//        paymentService.processPayment(booking);
        boolean paymentSuccess = paymentService.processPayment(booking);

        if(paymentSuccess){

            PaymentSuccessEvent successEvent = new PaymentSuccessEvent();
            successEvent.setBookingId(event.getBookingId());

            kafkaTemplate.send("payment-success", successEvent);

        } else {

            PaymentFailedEvent failedEvent = new PaymentFailedEvent();
            failedEvent.setBookingId(event.getBookingId());

            kafkaTemplate.send("payment-failed", failedEvent);
        }
    }
        // fetch seats via mapping
//        List<BookingSeat> bookingSeats =
//                bookingSeatRepository.findByBookingBookingId(booking.getBookingId());
//
//        List<ShowSeat> seats = bookingSeats.stream()
//                .map(BookingSeat::getShowSeat)
//                .toList();
//
//        if(paymentSuccess){
//
//            logger.info("Payment SUCCESS for booking {}", booking.getBookingId());
//
//            booking.setStatus("BOOKED");
//
//            for(ShowSeat seat : seats){
//                seat.setStatus(SeatStatus.BOOKED);
//            }
//
//        } else {
//
//            logger.warn("Payment FAILED for booking {}", booking.getBookingId());
//
//            booking.setStatus("FAILED");
//
//            for(ShowSeat seat : seats){
//                seat.setStatus(SeatStatus.AVAILABLE);
//            }
//        }
//
//        showSeatRepository.saveAll(seats);
//        bookingRepository.save(booking);
//    }
}
