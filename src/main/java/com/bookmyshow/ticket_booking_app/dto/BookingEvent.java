package com.bookmyshow.ticket_booking_app.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookingEvent {

    private Long bookingId;
    private Long userId;
    private Long showId;
    private List<Long> seatIds;
    private double amount;

    // getters setters
}
