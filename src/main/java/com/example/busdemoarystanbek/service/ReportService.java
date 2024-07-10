package com.example.busdemoarystanbek.service;

import com.example.busdemoarystanbek.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReportService {

    private final TicketRepository ticketRepository;

    @Autowired
    public ReportService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

}

