package com.bookmyshow.ticket_booking_app.controller;

import com.bookmyshow.ticket_booking_app.dto.BookingRequest;
import com.bookmyshow.ticket_booking_app.entity.Booking;
import com.bookmyshow.ticket_booking_app.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest request){

        Booking booking = bookingService.createBooking(request);

        return ResponseEntity.ok(booking);
    }
}
