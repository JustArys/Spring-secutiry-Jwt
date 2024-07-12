package com.example.busdemoarystanbek.service;

import com.example.busdemoarystanbek.model.Bus;
import com.example.busdemoarystanbek.model.Route;
import com.example.busdemoarystanbek.model.Ticket;
import com.example.busdemoarystanbek.model.User;
import com.example.busdemoarystanbek.repository.BusRepository;
import com.example.busdemoarystanbek.repository.RouteRepository;
import com.example.busdemoarystanbek.repository.TicketRepository;
import com.example.busdemoarystanbek.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final RouteRepository routeRepository;
    private final BusRepository busRepository;
    private final EmailService emailService;

    public Ticket reserveTicket(User user, Long routeId){
        Route route = routeRepository.findById(routeId).orElseThrow(() -> new IllegalArgumentException("Invalid trip ID"));
        Bus bus = route.getBus();
        Ticket ticket = new Ticket();
        LocalDateTime departureTime = route.getDepartureTime();
        LocalDateTime now = LocalDateTime.now();
        long hoursUntilDeparture = now.until(departureTime, ChronoUnit.HOURS);
        if (bus.getAvailableSeats() > 0){
            ticket.setUser(user);
            ticket.setRoute(route);
            ticket.setBookingTime(LocalDateTime.now());
            ticketRepository.save(ticket);

            bus.setAvailableSeats(bus.getAvailableSeats()-1);
            busRepository.save(bus);
            SimpleMailMessage message = getSimpleMailMessage(user, route);
            emailService.sendEmail(message);
        }
        else if(hoursUntilDeparture < 3){
            throw new RuntimeException("Unable to get ticket, no time left");
        }
        else {
            throw new RuntimeException("No more seats available");
        }
        return ticket;
    }


    public Ticket deleteTicket(User user, Long id){
        Ticket ticketUser = ticketRepository.findById(id).orElseThrow();
        if(ticketUser.getUser() == user){
            ticketRepository.delete(ticketUser);
            return ticketUser;
        }
        else {
            throw new RuntimeException("No tickets with this id");
        }
    }

    public Ticket getTicket(User user, Long id){
        Ticket ticket = ticketRepository.findById(id).orElseThrow();
        if(ticket.getUser() == user){
            return ticket;
        }
        else throw new RuntimeException("You don't own this ticket");
    }

    private static SimpleMailMessage getSimpleMailMessage(User user, Route route) {
        String subject = "Ticket Reservation Confirmation";
        String messageText = String.format("Dear %s %s, your ticket for the trip from %s to %s has been successfully reserved. Departure: %s, Arrival: %s",
                user.getUserInfo().getFirstName(),user.getUserInfo().getLastName(), route.getRouteFrom(), route.getRouteTo(), route.getDepartureTime(), route.getArrivalTime());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(messageText);
        return message;
    }
}
