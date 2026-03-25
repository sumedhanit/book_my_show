package com.bookmyshow.ticket_booking_app.service;

import com.bookmyshow.ticket_booking_app.entity.Booking;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void sendBookingSuccessEmail(Booking booking) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(booking.getUser().getEmail());
        message.setSubject("Ticket Booking Confirmed");

        message.setText(
                "Your ticket has been booked successfully!\n\n" +
                        "Booking ID: " + booking.getBookingId() + "\n" +
                        "Show ID: " + booking.getShow().getShowId() + "\n" +
                        "Total Price: " + booking.getTotalPrice() + "\n\n" +
                        "Enjoy your movie!"
        );

        mailSender.send(message);

    }
    public void sendBookingFailureEmail(Booking booking) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(booking.getUser().getEmail());
        message.setSubject("Ticket Booking Failed");

        message.setText(
                "Your ticket booking failed due to payment failure.\n\n" +
                        "Booking ID: " + booking.getBookingId() + "\n\n" +
                        "Please try again."
        );

        mailSender.send(message);
    }
}
