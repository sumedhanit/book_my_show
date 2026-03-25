package com.bookmyshow.ticket_booking_app.service;

import com.bookmyshow.ticket_booking_app.entity.City;
import com.bookmyshow.ticket_booking_app.entity.Movie;
import com.bookmyshow.ticket_booking_app.repository.CityRepository;
import com.bookmyshow.ticket_booking_app.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CityRepository cityRepository;

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public City addCity(City city) {
        return cityRepository.save(city);
    }
}
