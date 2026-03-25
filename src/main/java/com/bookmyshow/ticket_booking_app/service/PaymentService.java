package com.bookmyshow.ticket_booking_app.service;

import com.bookmyshow.ticket_booking_app.entity.Booking;
import com.bookmyshow.ticket_booking_app.entity.Payment;
import com.bookmyshow.ticket_booking_app.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    public boolean processPayment(Booking booking) {

        logger.info("Processing payment for booking {}", booking.getBookingId());

        // ✅ 1. Idempotency check (VERY IMPORTANT)
        Payment existingPayment = paymentRepository
                .findByBookingBookingId(booking.getBookingId())
                .orElse(null);

        if (existingPayment != null) {
            logger.warn("Payment already processed for booking {}", booking.getBookingId());

            return "SUCCESS".equals(existingPayment.getPaymentStatus());
        }

        Payment payment = new Payment();

        payment.setBooking(booking);
        payment.setAmount(booking.getTotalPrice());
        payment.setPaymentMethod("PhonePay");
        payment.setCreatedAt(LocalDateTime.now());

        try {
            logger.info("Simulating payment gateway delay...");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // simulate success
        boolean success = true;

        payment.setPaymentStatus(success ? "SUCCESS" : "FAILED");

        try {
            paymentRepository.save(payment);
        } catch (Exception e) {
            logger.error("Error while saving payment: {}", e.getMessage());

            // ✅ Handle duplicate safely (Kafka retry case)
            Payment alreadySaved = paymentRepository
                    .findByBookingBookingId(booking.getBookingId())
                    .orElse(null);

            if (alreadySaved != null) {
                return "SUCCESS".equals(alreadySaved.getPaymentStatus());
            }

            throw e; // real failure
        }

        return success;
    }
}
