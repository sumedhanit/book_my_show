package com.bookmyshow.ticket_booking_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long theatreId;

    private String name;

    private String address;

    @ManyToOne()
    @JoinColumn(name="city_id",referencedColumnName = "cityId")
    private City city;

    @OneToMany(mappedBy = "theatre")
    @JsonIgnore
    private List<Screen> screens;

}
