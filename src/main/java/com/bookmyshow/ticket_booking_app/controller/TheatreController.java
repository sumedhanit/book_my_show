package com.bookmyshow.ticket_booking_app.controller;

import com.bookmyshow.ticket_booking_app.entity.Theatre;
import com.bookmyshow.ticket_booking_app.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theatres")
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    @PostMapping
    public Theatre addTheatre(@RequestBody Theatre theatre) {
        return theatreService.addTheatre(theatre);
    }
    @GetMapping
    public List<Theatre> getAllTheatres(){
        return theatreService.getAllTheatres();
    }

    @GetMapping("/{id}")
    public Theatre getTheatreById(@PathVariable Long id){
        return theatreService.getTheatreById(id);
    }

    @GetMapping("/city/{cityId}")
    public List<Theatre> getTheatreByCity(@PathVariable Long cityId){
        return theatreService.getTheatreByCity(cityId);
    }

//    @GetMapping("/city/{cityId}")
//    public List<Theatre> getTheatresByCity(@PathVariable Long cityId) {
//        return theatreService.getTheatresByCity(cityId);
//    }
}
