package com.example.busdemoarystanbek.service;

import com.example.busdemoarystanbek.model.Report;
import com.example.busdemoarystanbek.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private TicketRepository ticketRepository;

    public Report getTicketSalesReport(LocalDateTime startDate, LocalDateTime endDate) {
        List<Report> reports = ticketRepository.findTicketSalesReport(startDate, endDate);
        return reports.isEmpty() ? null : reports.getFirst();
    }

    public Report getFlightLoadAnalytics(LocalDateTime startDate, LocalDateTime endDate) {
        List<Report> reports = ticketRepository.findFlightLoadAnalytics(startDate, endDate);
        return reports.isEmpty() ? null : reports.getFirst();
    }

    public Report getPassengerAndFlightStatistics(LocalDateTime startDate, LocalDateTime endDate) {
        List<Report> reports = ticketRepository.findPassengerAndFlightStatistics(startDate, endDate);
        return reports.isEmpty() ? null : reports.getFirst();
    }
}
