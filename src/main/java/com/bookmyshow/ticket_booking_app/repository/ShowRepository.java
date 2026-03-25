package com.bookmyshow.ticket_booking_app.repository;

import com.bookmyshow.ticket_booking_app.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByMovieMovieId(Long movieId);

    List<Show> findByScreenScreenId(Long screenId);

}
