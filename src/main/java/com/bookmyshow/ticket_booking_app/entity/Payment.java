package com.bookmyshow.ticket_booking_app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne
    @JoinColumn(name="booking_id",referencedColumnName = "bookingId")
    private Booking booking;

    private Double amount;

    private String paymentMethod;

    private String paymentStatus;

    private LocalDateTime createdAt;

}
