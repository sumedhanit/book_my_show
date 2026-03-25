package com.bookmyshow.ticket_booking_app.controller;

import com.bookmyshow.ticket_booking_app.entity.Show;
import com.bookmyshow.ticket_booking_app.entity.ShowSeat;
import com.bookmyshow.ticket_booking_app.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping
    public Show createShow(@RequestBody Show show){
        return showService.createShow(show);
    }

    @GetMapping("/movie/{movieId}")
    public List<Show> getShowsByMovie(@PathVariable Long movieId){
        return showService.getShowsByMovie(movieId);
    }

    @PostMapping("/seats")
    public ShowSeat createShowSeat(@RequestBody ShowSeat showSeat){
        return showService.createShowSeat(showSeat);
    }
}
