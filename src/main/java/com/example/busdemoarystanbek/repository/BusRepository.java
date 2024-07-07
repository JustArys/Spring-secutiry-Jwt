package com.example.busdemoarystanbek.repository;

import com.example.busdemoarystanbek.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusRepository extends JpaRepository<Bus, Long> {
    public Bus findByDriverName(String driverName);

    public List<Bus> findByBusType(String busType);

    public List<Bus> findByRouteFromAndRouteTo(String routeFrom,String routeTo);
}
