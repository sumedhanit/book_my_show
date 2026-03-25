package com.bookmyshow.ticket_booking_app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "show_id",referencedColumnName = "showId")
    private Show show;

    private Double totalPrice;

    private String status;

}
