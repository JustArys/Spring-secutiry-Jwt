package com.example.busdemoarystanbek.repository;

import com.example.busdemoarystanbek.model.Report;
import com.example.busdemoarystanbek.model.Ticket;
import com.example.busdemoarystanbek.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUser(User user);

    List<Report> findTicketSalesReport(LocalDateTime startDate, LocalDateTime endDate);

    List<Report> findFlightLoadAnalytics(LocalDateTime startDate, LocalDateTime endDate);

    List<Report> findPassengerAndFlightStatistics(LocalDateTime startDate, LocalDateTime endDate);
}

