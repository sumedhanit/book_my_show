package com.bookmyshow.ticket_booking_app.repository;

import com.bookmyshow.ticket_booking_app.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
}
