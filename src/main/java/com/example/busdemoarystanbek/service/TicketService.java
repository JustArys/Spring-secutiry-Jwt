package com.example.busdemoarystanbek.service;

import com.example.busdemoarystanbek.model.Bus;
import com.example.busdemoarystanbek.model.Route;
import com.example.busdemoarystanbek.model.Ticket;
import com.example.busdemoarystanbek.model.User;
import com.example.busdemoarystanbek.repository.BusRepository;
import com.example.busdemoarystanbek.repository.RouteRepository;
import com.example.busdemoarystanbek.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final RouteRepository routeRepository;
    private final BusRepository busRepository;
    private final EmailService emailService;

    public Ticket reserveTicket(User user, Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trip ID"));
        Bus bus = route.getBus();

        if (bus.getAvailableSeats() > 0) {
            Ticket ticket = new Ticket();
            ticket.setUser(user);
            ticket.setRoute(route);
            ticket.setBookingTime(LocalDateTime.now());
            ticketRepository.save(ticket);

            bus.setAvailableSeats(bus.getAvailableSeats() - 1);
            busRepository.save(bus);

            SimpleMailMessage message = getSimpleMailMessage(user, route);
            emailService.sendEmail(message);

            return ticket;
        } else {
            throw new RuntimeException("No more seats available");
        }
    }

    public Ticket deleteTicket(User user, Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ticket not found"));

        if (ticket.getUser().equals(user)) {
            ticketRepository.delete(ticket);
            return ticket;
        } else {
            throw new RuntimeException("You don't own this ticket");
        }
    }

    public Ticket getTicket(User user, Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ticket not found"));

        if (ticket.getUser().equals(user)) {
            return ticket;
        } else {
            throw new RuntimeException("You don't own this ticket");
        }
    }

    private SimpleMailMessage getSimpleMailMessage(User user, Route route) {
        String subject = "Ticket Reservation Confirmation";
        String messageText = String.format("Dear %s, your ticket for the trip from %s to %s has been successfully reserved. Departure: %s, Arrival: %s",
                user.getUserInfo().getFirstName(), route.getRouteFrom(), route.getRouteTo(), route.getDepartureTime(), route.getArrivalTime());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(messageText);
        return message;
    }
}