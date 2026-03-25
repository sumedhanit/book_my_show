package com.bookmyshow.ticket_booking_app.repository;

import com.bookmyshow.ticket_booking_app.entity.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingSeatRepository extends JpaRepository<BookingSeat,Long> {
    List<BookingSeat> findByBookingBookingId(Long bookingId);
}
