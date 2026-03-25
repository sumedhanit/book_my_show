package com.bookmyshow.ticket_booking_app.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookingRequest {

    private Long showId;

    private Long userId;

    private List<Long> seatIds;

}
