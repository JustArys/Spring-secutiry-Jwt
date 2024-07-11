package com.example.busdemoarystanbek.repository;

import com.example.busdemoarystanbek.model.Bus;
import com.example.busdemoarystanbek.model.Driver;
import com.example.busdemoarystanbek.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findByRouteFromAndRouteToAndDepartureTimeAndArrivalTime(String routeFrom, String routeTo, LocalDateTime departureTime, LocalDateTime arrivalTime);

    List<Route> findByRouteFromAndRouteToAndDepartureTime(String routeFrom, String routeTo, LocalDateTime departureTime);
    List<Route> findByRouteFromAndRouteToAndDepartureTimeAfter(String routeFrom, String routeTo, LocalDateTime departureTime);
    List<Route> findByRouteFromAndRouteToAndArrivalTime(String routeFrom, String routeTo, LocalDateTime arrivalTime);
    List<Route> findByRouteFromAndRouteToAndArrivalTimeBefore(String routeFrom, String routeTo, LocalDateTime arrivalTime);


    List<Route> findByRouteFromAndRouteTo(String routeFrom, String routeTo);
    List<Route> findByRouteFrom(String routeFrom);
    List<Route> findByRouteTo(String routeTo);

    List<Route> findByDepartureTimeAndArrivalTime(LocalDateTime departureTime, LocalDateTime arrivalTime);
    List<Route> findByDepartureTime(LocalDateTime departureTime);
    List<Route> findByArrivalTime(LocalDateTime arrivalTime);

    List<Route> findByBusAndDepartureTimeBetween(Bus bus, LocalDateTime departureTime, LocalDateTime arrivalTime);
    List<Route> findByBusAndArrivalTimeBetween(Bus bus, LocalDateTime departureTime, LocalDateTime arrivalTime);

    List<Route> findByDriverAndDepartureTimeBetween(Driver driver, LocalDateTime departureTime, LocalDateTime arrivalTime);
    List<Route> findByDriverAndArrivalTimeBetween(Driver driver, LocalDateTime departureTime, LocalDateTime arrivalTime);
}
