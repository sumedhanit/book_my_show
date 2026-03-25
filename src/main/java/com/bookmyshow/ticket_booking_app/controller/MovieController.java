package com.bookmyshow.ticket_booking_app.controller;

import com.bookmyshow.ticket_booking_app.entity.City;
import com.bookmyshow.ticket_booking_app.entity.Movie;
import com.bookmyshow.ticket_booking_app.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }

    @GetMapping
    public List<Movie> getMovies() {
        return movieService.getMovies();
    }

    @PostMapping("/city")
    public City addCity(@RequestBody City city) {
        return movieService.addCity(city);
    }


}
