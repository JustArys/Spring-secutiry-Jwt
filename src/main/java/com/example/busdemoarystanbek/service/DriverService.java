package com.example.busdemoarystanbek.service;

import com.example.busdemoarystanbek.model.Driver;
import com.example.busdemoarystanbek.model.DriverSchedule;
import com.example.busdemoarystanbek.model.Route;
import com.example.busdemoarystanbek.repository.DriverRepository;
import com.example.busdemoarystanbek.repository.DriverScheduleRepository;
import com.example.busdemoarystanbek.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;
    private final RouteRepository routeRepository;
    private final DriverScheduleRepository driverScheduleRepository;

    public Driver addDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Driver updateDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public void deleteDriver(Long driverId) {
        driverRepository.deleteById(driverId);
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver getDriverById(Long driverId) {
        return driverRepository.findById(driverId).orElse(null);
    }

    // Assigning drivers to routes based on their schedules
    public void assignDriverToRoute(Long routeId, Long driverId) {
        Route route = routeRepository.findById(routeId).orElseThrow(() -> new RuntimeException("Route not found"));
        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new RuntimeException("Driver not found"));

        // Check if the driver is already assigned to another route at the same time
        List<Route> conflictingDepartureRoutes = routeRepository.findByDriverAndDepartureTimeBetween(
                driver, route.getDepartureTime(), route.getArrivalTime());
        List<Route> conflictingArrivalRoutes = routeRepository.findByDriverAndArrivalTimeBetween(
                driver, route.getDepartureTime(), route.getArrivalTime());

        if (!conflictingDepartureRoutes.isEmpty() || !conflictingArrivalRoutes.isEmpty()) {
            throw new IllegalStateException("The driver is already assigned to another route during this time");
        }

        // Check if the current location matches
        if (!driver.getCurrentLocation().equals(route.getRouteFrom())) {
            throw new RuntimeException("Driver is not available at the route's departure location");
        }

        // Check for scheduling conflicts
        LocalDateTime routeStart = route.getDepartureTime();
        LocalDateTime routeEnd = route.getArrivalTime();

        List<DriverSchedule> schedules = driverScheduleRepository.findByDriverId(driverId);
        for (DriverSchedule schedule : schedules) {
            if (routeStart.isBefore(schedule.getEndTime()) && routeEnd.isAfter(schedule.getStartTime())) {
                throw new RuntimeException("Driver has a conflicting schedule");
            }
        }

        // Assigning a driver to a route
        route.setDriver(driver);
        routeRepository.save(route);

        // Update the driver's current location
        driver.setCurrentLocation(route.getRouteTo());
        driverRepository.save(driver);

        // Adding a schedule
        DriverSchedule newSchedule = new DriverSchedule();
        newSchedule.setDriver(driver);
        newSchedule.setRoute(route);
        newSchedule.setStartTime(routeStart);
        newSchedule.setEndTime(routeEnd);
        driverScheduleRepository.save(newSchedule);
    }
}
