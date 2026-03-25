package com.bookmyshow.ticket_booking_app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showId;

    @ManyToOne
    @JoinColumn(name="movie_id",referencedColumnName = "movieId")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name="screen_id",referencedColumnName = "screenId")
    private Screen screen;

    private LocalDate showDate;

    private LocalTime showTime;

    private Double price;

}
