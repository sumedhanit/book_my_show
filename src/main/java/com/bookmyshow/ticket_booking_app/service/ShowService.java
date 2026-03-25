package com.bookmyshow.ticket_booking_app.service;

import com.bookmyshow.ticket_booking_app.entity.Seat;
import com.bookmyshow.ticket_booking_app.entity.SeatStatus;
import com.bookmyshow.ticket_booking_app.entity.Show;
import com.bookmyshow.ticket_booking_app.entity.ShowSeat;
import com.bookmyshow.ticket_booking_app.repository.SeatRepository;
import com.bookmyshow.ticket_booking_app.repository.ShowRepository;
import com.bookmyshow.ticket_booking_app.repository.ShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    public Show createShow(Show show){
        Show savedShow= showRepository.save(show);
        // 2 Fetch seats of the screen
        Long screenId = show.getScreen().getScreenId();

        List<Seat> seats = seatRepository.findByScreenScreenId(screenId);

        // 3 Create ShowSeat entries
        List<ShowSeat> showSeats = new ArrayList<>();

        for(Seat seat : seats){

            ShowSeat showSeat = new ShowSeat();
            showSeat.setShow(savedShow);
            showSeat.setSeat(seat);
            showSeat.setStatus(SeatStatus.AVAILABLE);
            showSeats.add(showSeat);
        }

        // 4 Save all show seats
        showSeatRepository.saveAll(showSeats);

        return savedShow;
    }

    public List<Show> getShowsByMovie(Long movieId){
        return showRepository.findByMovieMovieId(movieId);
    }

    public List<Show> getShowsByScreen(Long screenId){
        return showRepository.findByScreenScreenId(screenId);
    }

    public ShowSeat createShowSeat(ShowSeat showSeat) {
        return showSeatRepository.save(showSeat);
    }
}
