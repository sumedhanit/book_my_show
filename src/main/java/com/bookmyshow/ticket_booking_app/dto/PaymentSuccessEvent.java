package com.bookmyshow.ticket_booking_app.dto;

import lombok.Data;

@Data
public class PaymentSuccessEvent {
    private Long bookingId;
}
