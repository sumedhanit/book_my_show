package com.bookmyshow.ticket_booking_app.repository;

import com.bookmyshow.ticket_booking_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
