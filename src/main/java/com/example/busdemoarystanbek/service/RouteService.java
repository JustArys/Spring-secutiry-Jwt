package com.example.busdemoarystanbek.service;

import com.example.busdemoarystanbek.model.Bus;
import com.example.busdemoarystanbek.model.Route;
import com.example.busdemoarystanbek.repository.BusRepository;
import com.example.busdemoarystanbek.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final BusRepository busRepository;
    private final RouteRepository routeRepository;

    public Route addRoute(Route route){
        return routeRepository.save(route);
    }
    public Route updateRoute(Route route,Route updateRoute){
        updateRoute.setRouteTo(route.getRouteTo());
        updateRoute.setRouteFrom(route.getRouteFrom());
        updateRoute.setDistance(route.getDistance());
        updateRoute.setDepartureTime(route.getDepartureTime());
        updateRoute.setArrivalTime(route.getArrivalTime());
        return routeRepository.save(updateRoute);
    }

    public Route deleteRoute(Long id) {
        Route route = routeRepository.findById(id).get();
         routeRepository.deleteById(id);
         return route;
    }

    public void assignBusToRoute(Long routeId, Long busId){
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found"));
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        // Check if the bus is already assigned to another route at the same time
        List<Route> conflictingDepartureRoutes = routeRepository.findByBusAndDepartureTimeBetween(
                bus, route.getDepartureTime(), route.getArrivalTime());
        List<Route> conflictingArrivalRoutes = routeRepository.findByBusAndArrivalTimeBetween(
                bus, route.getDepartureTime(), route.getArrivalTime());

        if (!conflictingDepartureRoutes.isEmpty() || !conflictingArrivalRoutes.isEmpty()) {
            throw new IllegalStateException("The bus is already assigned to another route during this time");
        }

        route.setBus(bus);
        routeRepository.save(route);
    }

    public Route viewRoute(Long id){
        return findRouteById(id);
    }

    public List<Route> viewAllRoute(){
        return routeRepository.findAll();
    }

    public Route findRouteById(Long id){
        return routeRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format("No such route with id '%d' not found", id)));
    }

    public List<Route> searchRoutes(
            String routeFrom, String routeTo,
            LocalDateTime departureTime, LocalDateTime arrivalTime) {
        if (routeFrom != null && routeTo != null && departureTime != null && arrivalTime != null) {
            // Search by all criteria
            return routeRepository.findByRouteFromAndRouteToAndDepartureTimeAndArrivalTime(
                    routeFrom, routeTo, departureTime, arrivalTime);
        } else if (routeFrom != null && routeTo != null && departureTime != null) {
            // Routes/time
            List<Route> routesWithExactDepartureTime = routeRepository.findByRouteFromAndRouteToAndDepartureTime(routeFrom, routeTo, departureTime);
            List<Route> routesAfterDepartureTime = routeRepository.findByRouteFromAndRouteToAndDepartureTimeAfter(routeFrom, routeTo, departureTime);

            // Combine the results
            List<Route> combinedRoutes = new ArrayList<>();
            combinedRoutes.addAll(routesWithExactDepartureTime);
            combinedRoutes.addAll(routesAfterDepartureTime);

            return combinedRoutes;
        } else if (routeFrom != null && routeTo != null && arrivalTime != null) {
            List<Route> routesWithExactArrivalTime = routeRepository.findByRouteFromAndRouteToAndArrivalTime(routeFrom, routeTo, arrivalTime);
            List<Route> routesBeforeArrivalTime = routeRepository.findByRouteFromAndRouteToAndArrivalTimeBefore(routeFrom, routeTo, arrivalTime);

            List<Route> combinedRoutes = new ArrayList<>();
            combinedRoutes.addAll(routesWithExactArrivalTime);
            combinedRoutes.addAll(routesBeforeArrivalTime);

            return combinedRoutes;
        } else if (routeFrom != null && routeTo != null) {
            // Routes
            return routeRepository.findByRouteFromAndRouteTo(routeFrom, routeTo);
        } else if (routeFrom != null) {
            return  routeRepository.findByRouteFrom(routeFrom);
        } else if (routeTo != null) {
            return routeRepository.findByRouteTo(routeTo);
        } else if (departureTime != null && arrivalTime != null) {
            // By time
            return routeRepository.findByDepartureTimeAndArrivalTime(departureTime, arrivalTime);
        } else if (departureTime != null) {
            return routeRepository.findByDepartureTime(departureTime);
        } else if (arrivalTime != null) {
            return routeRepository.findByArrivalTime(arrivalTime);
        } else {
            return routeRepository.findAll();
        }
    }
}
