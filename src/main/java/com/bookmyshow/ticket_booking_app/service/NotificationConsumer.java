package com.bookmyshow.ticket_booking_app.service;

import com.bookmyshow.ticket_booking_app.dto.PaymentFailedEvent;
import com.bookmyshow.ticket_booking_app.dto.PaymentSuccessEvent;
import com.bookmyshow.ticket_booking_app.entity.Booking;
import com.bookmyshow.ticket_booking_app.entity.User;
import com.bookmyshow.ticket_booking_app.repository.BookingRepository;
import com.bookmyshow.ticket_booking_app.repository.BookingSeatRepository;
import com.bookmyshow.ticket_booking_app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private static final Logger logger =
            LoggerFactory.getLogger(NotificationConsumer.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    @KafkaListener(topics = "payment-success", groupId = "notification-group")
    public void sendSuccessEmail(PaymentSuccessEvent event){

        logger.info("Sending SUCCESS email for booking {}", event.getBookingId());
        Booking booking = bookingRepository.findById(event.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        notificationService.sendBookingSuccessEmail(booking);
    }

    @KafkaListener(topics = "payment-failed", groupId = "notification-group")
    public void sendFailureEmail(PaymentFailedEvent event){

        logger.info("Sending FAILURE email for booking {}", event.getBookingId());
        Booking booking = bookingRepository.findById(event.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        notificationService.sendBookingFailureEmail(booking);
    }
}
