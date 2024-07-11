package com.example.busdemoarystanbek.service;

import com.example.busdemoarystanbek.model.Analytics;
import com.example.busdemoarystanbek.model.Report;
import com.example.busdemoarystanbek.model.Statistics;
import com.example.busdemoarystanbek.repository.AnalyticsRepository;
import com.example.busdemoarystanbek.repository.ReportRepository;
import com.example.busdemoarystanbek.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final StatisticsRepository statisticsRepository;
    private final AnalyticsRepository analyticsRepository;

    public Report saveReport(Report report, Long statisticsId, Long analyticsId) {
        Optional<Statistics> statistics = statisticsRepository.findById(statisticsId);
        Optional<Analytics> analytics = analyticsRepository.findById(analyticsId);

        statistics.ifPresent(report::setStatistics);
        analytics.ifPresent(report::setAnalytics);

        return reportRepository.save(report);
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }
}
