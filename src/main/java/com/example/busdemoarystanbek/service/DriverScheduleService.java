package com.example.busdemoarystanbek.service;

import com.example.busdemoarystanbek.model.DriverSchedule;
import com.example.busdemoarystanbek.repository.DriverScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverScheduleService {
    private final DriverScheduleRepository driverScheduleRepository;

    public DriverSchedule addSchedule(DriverSchedule schedule) {
        return driverScheduleRepository.save(schedule);
    }

    public DriverSchedule updateSchedule(DriverSchedule schedule) {
        return driverScheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long scheduleId) {
        driverScheduleRepository.deleteById(scheduleId);
    }

    public List<DriverSchedule> viewAllSchedules() {
        return driverScheduleRepository.findAll();
    }

    public DriverSchedule viewScheduleById(Long scheduleId) {
        return driverScheduleRepository.findById(scheduleId).orElse(null);
    }

    public List<DriverSchedule> viewScheduleByDriverId(Long driverId) {
        return driverScheduleRepository.findByDriverId(driverId);
    }
}
