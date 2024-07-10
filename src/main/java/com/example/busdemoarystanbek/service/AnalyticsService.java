package com.example.busdemoarystanbek.service;

import com.example.busdemoarystanbek.model.Analytics;
import com.example.busdemoarystanbek.model.Bus;
import com.example.busdemoarystanbek.model.request.AnalyticsRequest;
import com.example.busdemoarystanbek.repository.AnalyticsRepository;
import com.example.busdemoarystanbek.repository.BusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final AnalyticsRepository analyticsRepository;
    private final BusRepository busRepository;

    public Analytics createAnalytics(AnalyticsRequest analyticsRequest) {
        Bus bus = busRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("Bus not found"));
        Analytics analytics = Analytics.builder()
                .tripsAmount(analyticsRequest.getTripsAmount())
                .ticketsSold(analyticsRequest.getTicketsSold())
                .bus(bus)
                .build();
        return analyticsRepository.save(analytics);
    }

    public List<Analytics> getAllAnalytics() {
        return analyticsRepository.findAll();
    }

    public Analytics getAnalyticsById(Long id) {
        return analyticsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Analytics not found"));
    }
}
