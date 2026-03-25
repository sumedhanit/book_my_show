package com.bookmyshow.ticket_booking_app.service;

import com.bookmyshow.ticket_booking_app.entity.Screen;
import com.bookmyshow.ticket_booking_app.entity.Seat;
import com.bookmyshow.ticket_booking_app.repository.ScreenRepository;
import com.bookmyshow.ticket_booking_app.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenService {

    @Autowired
    private ScreenRepository screenRepository;
    @Autowired
    private SeatRepository seatRepository;

    public Screen addScreen(Screen screen){
        return screenRepository.save(screen);
    }

    public List<Screen> getScreensByTheatre(Long theatreId){
        return screenRepository.findByTheatreTheatreId(theatreId);
    }

    public Screen getScreen(Long id) {
        return screenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Screen not found"));
    }

    public Seat addSeatToScreen(Long screenId, Seat seat){

        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new RuntimeException("Screen not found"));

        seat.setScreen(screen);

        return seatRepository.save(seat);
    }

    public List<Seat> getSeatsByScreen(Long screenId){
        return seatRepository.findByScreenScreenId(screenId);
    }

}
