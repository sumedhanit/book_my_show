package com.bookmyshow.ticket_booking_app.controller;

import com.bookmyshow.ticket_booking_app.entity.Screen;
import com.bookmyshow.ticket_booking_app.entity.Seat;
import com.bookmyshow.ticket_booking_app.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/screens")
public class ScreenController {

    @Autowired
    private ScreenService screenService;

    @PostMapping
    public Screen addScreen(@RequestBody Screen screen){
        return screenService.addScreen(screen);
    }

    @GetMapping("/theatre/{theatreId}")
    public List<Screen> getScreensByTheatre(@PathVariable Long theatreId){
        return screenService.getScreensByTheatre(theatreId);
    }
    @PostMapping("/{screenId}/seats")
    public Seat addSeat(
            @PathVariable Long screenId,
            @RequestBody Seat seat
    ){
        return screenService.addSeatToScreen(screenId, seat);
    }

    @GetMapping("/{screenId}/seats")
    public List<Seat> getSeats(@PathVariable Long screenId){
        return screenService.getSeatsByScreen(screenId);
    }

}
