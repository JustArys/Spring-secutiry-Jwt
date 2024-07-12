package com.example.busdemoarystanbek.service;

import com.example.busdemoarystanbek.model.Route;
import com.example.busdemoarystanbek.model.Ticket;
import com.example.busdemoarystanbek.model.User;
import com.example.busdemoarystanbek.repository.RouteRepository;
import com.example.busdemoarystanbek.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailSchedulerService {
    private final RouteRepository routeRepository;
    private final EmailService emailService;
    private final TicketRepository ticketRepository;

    @Scheduled(fixedRate = 360000)
    public void sendReminderEmails() {
        LocalDateTime now = LocalDateTime.now();

        List<Route> routeList = routeRepository.findAll();

        for (Route route : routeList){
            LocalDateTime departureTime = route.getDepartureTime();
            long hoursUntilDeparture = now.until(departureTime, ChronoUnit.HOURS);

            if (hoursUntilDeparture == 8){
                List<Ticket> tickets = ticketRepository.findByRoute(route);
                for (Ticket ticket : tickets){
                    User user = ticket.getUser();
                    SimpleMailMessage message = getReminderMessage(user, route);
                    emailService.sendEmail(message);
                }

            }
        }
    }

    private SimpleMailMessage getReminderMessage(User user, Route route) {
        String subject = "Upcoming Trip Reminder";
        String messageText = String.format("Dear %s %s, this is a reminder for your upcoming trip from %s to %s. Departure: %s, Arrival: %s",
                user.getUserInfo().getFirstName(), user.getUserInfo().getLastName(), route.getRouteFrom(), route.getRouteTo(), route.getDepartureTime(), route.getArrivalTime());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(messageText);
        return message;
    }
}
