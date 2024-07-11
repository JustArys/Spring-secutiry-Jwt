package com.example.busdemoarystanbek.controller;

import com.example.busdemoarystanbek.model.Statistics;
import com.example.busdemoarystanbek.model.request.StatisticsRequest;
import com.example.busdemoarystanbek.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;

    @PostMapping
    public Statistics createStatistics(@RequestBody StatisticsRequest request) {
        Statistics statistics = Statistics.builder()
                .ticketSoldAmount(request.getTicketSoldAmount())
                .ticketSoldTotal(request.getTicketSoldTotal())
                .build();
        return statisticsService.saveStatistics(statistics);
    }

    @GetMapping
    public List<Statistics> getAllStatistics() {
        return statisticsService.getAllStatistics();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Statistics> getStatisticsById(@PathVariable Long id) {
        Optional<Statistics> statistics = statisticsService.getStatisticsById(id);
        return statistics.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
