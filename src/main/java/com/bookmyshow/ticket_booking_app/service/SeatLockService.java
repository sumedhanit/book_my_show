package com.bookmyshow.ticket_booking_app.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SeatLockService {

    @Autowired
    private RedissonClient redissonClient;

    public List<RLock> lockSeats(List<Long> seatIds){

        List<RLock> locks = new ArrayList<>();

        for(Long seatId : seatIds){

            String lockKey = "lock:showSeat:" + seatId;

            RLock lock = redissonClient.getLock(lockKey);

            try {

                boolean acquired = lock.tryLock(
                        5,     // wait time
                        60,    // lease time
                        TimeUnit.SECONDS
                );

                if(!acquired){
                    throw new RuntimeException("Seat already locked: " + seatId);
                }

                locks.add(lock);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return locks;
    }

    public void releaseLocks(List<RLock> locks){

        for(RLock lock : locks){

            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }
}
