package com.example.busdemoarystanbek.controller;

import com.example.busdemoarystanbek.model.Report;
import com.example.busdemoarystanbek.model.request.ReportRequest;
import com.example.busdemoarystanbek.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody ReportRequest request) {
        Report report = Report.builder()
                .reportDescription(request.getReportDescription())
                .build();

        Report savedReport = reportService.saveReport(report, request.getStatisticsId(), request.getAnalyticsId());
        return ResponseEntity.ok(savedReport);
    }

    @GetMapping
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        Optional<Report> report = reportService.getReportById(id);
        return report.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
