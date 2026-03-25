package com.bookmyshow.ticket_booking_app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    private String seatNumber;

    private String category;

    @ManyToOne
    @JoinColumn(name="screen_id",referencedColumnName = "screenId")
    private Screen screen;

}
