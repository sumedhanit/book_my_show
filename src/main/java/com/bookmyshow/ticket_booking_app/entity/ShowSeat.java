package com.bookmyshow.ticket_booking_app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ShowSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showSeatId;

    @ManyToOne
    @JoinColumn(name="show_id",referencedColumnName = "showId")
    private Show show;

    @ManyToOne
    @JoinColumn(name="seat_id",referencedColumnName = "seatId")
    private Seat seat;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

}
