package com.example.busdemoarystanbek.service;

import com.example.busdemoarystanbek.model.Statistics;
import com.example.busdemoarystanbek.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;

    public Statistics saveStatistics(Statistics statistics) {
        return statisticsRepository.save(statistics);
    }

    public List<Statistics> getAllStatistics() {
        return statisticsRepository.findAll();
    }

    public Optional<Statistics> getStatisticsById(Long id) {
        return statisticsRepository.findById(id);
    }
}
