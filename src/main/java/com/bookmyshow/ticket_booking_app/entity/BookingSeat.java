package com.bookmyshow.ticket_booking_app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class BookingSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="booking_id",referencedColumnName = "bookingId")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name="show_seat_id",referencedColumnName = "showSeatId")
    private ShowSeat showSeat;

}
