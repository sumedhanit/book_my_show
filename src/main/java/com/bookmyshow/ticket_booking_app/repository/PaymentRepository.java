package com.bookmyshow.ticket_booking_app.repository;

import com.bookmyshow.ticket_booking_app.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
//    ScopedValue<Object> findByBookingBookingId(Long bookingId);
Optional<Payment> findByBookingBookingId(Long bookingId);
}
