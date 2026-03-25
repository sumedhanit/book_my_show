package com.bookmyshow.ticket_booking_app.service;

import com.bookmyshow.ticket_booking_app.entity.Theatre;
import com.bookmyshow.ticket_booking_app.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheatreService {

    @Autowired
    private TheatreRepository theatreRepository;

    public Theatre addTheatre(Theatre theatre) {
        return theatreRepository.save(theatre);
    }

    public List<Theatre> getAllTheatres(){
        return theatreRepository.findAll();
    }

    public Theatre getTheatreById(Long theatreId){
        return theatreRepository.findById(theatreId)
                .orElseThrow(() -> new RuntimeException("Theatre not found"));
    }

    public List<Theatre> getTheatreByCity(Long cityId){
        return theatreRepository.findByCityCityId(cityId);
    }
}
