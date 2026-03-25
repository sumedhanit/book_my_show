package com.bookmyshow.ticket_booking_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long screenId;

    private String name;

    @ManyToOne
    @JoinColumn(name="theatre_id",referencedColumnName = "theatreId")
    private Theatre theatre;

    @OneToMany(mappedBy = "screen")
    @JsonIgnore
    private List<Seat> seats;

}
