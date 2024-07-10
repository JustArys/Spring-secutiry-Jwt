package com.example.busdemoarystanbek.repository;

import com.example.busdemoarystanbek.model.DriverSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverScheduleRepository extends JpaRepository<DriverSchedule, Long> {
    List<DriverSchedule> findByDriverId(Long driverId);
}
