package com.bookmyshow.ticket_booking_app.repository;

import com.bookmyshow.ticket_booking_app.entity.ShowSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {

    List<ShowSeat> findByShowShowIdAndShowSeatIdIn(Long showId, List<Long> seatIds);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM ShowSeat s WHERE s.showSeatId IN :ids")
    List<ShowSeat> findSeatsForUpdate(@Param("ids") List<Long> ids);

}
