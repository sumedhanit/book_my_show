package com.bookmyshow.ticket_booking_app.service;

import com.bookmyshow.ticket_booking_app.dto.BookingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookingEventProducer {

    private static final Logger logger =
            LoggerFactory.getLogger(BookingEventProducer.class);

    private static final String TOPIC = "booking-created";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendBookingEvent(BookingEvent event){

        logger.info("Sending booking event to Kafka {}", event.getBookingId());

        kafkaTemplate.send(TOPIC, event);
    }
}
