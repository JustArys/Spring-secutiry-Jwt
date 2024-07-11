package com.example.busdemoarystanbek.controller;

import com.example.busdemoarystanbek.model.Analytics;
import com.example.busdemoarystanbek.model.request.AnalyticsRequest;
import com.example.busdemoarystanbek.service.AnalyticsService;
import com.example.busdemoarystanbek.service.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @PostMapping
    public ResponseEntity<Analytics> createAnalytics(@RequestBody AnalyticsRequest analyticsRequest) {
        Analytics analytics = analyticsService.createAnalytics(analyticsRequest);
        return new ResponseEntity<>(analytics, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Analytics>> getAllAnalytics() {
        List<Analytics> analyticsList = analyticsService.getAllAnalytics();
        return new ResponseEntity<>(analyticsList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Analytics> getAnalyticsById(@PathVariable Long id) {
        Analytics analytics = analyticsService.getAnalyticsById(id);
        return new ResponseEntity<>(analytics, HttpStatus.OK);
    }
}
