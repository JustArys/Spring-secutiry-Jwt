package com.example.busdemoarystanbek.controller;

import com.example.busdemoarystanbek.model.Route;
import com.example.busdemoarystanbek.model.Ticket;
import com.example.busdemoarystanbek.service.DriverService;
import com.example.busdemoarystanbek.service.RouteService;
import com.example.busdemoarystanbek.service.TicketService;
import com.example.busdemoarystanbek.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/route")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService rService;
    private final TicketService ticketService;
    private final UserService userService;
    private final DriverService driverService;

    @PostMapping("/addRoute")
    public ResponseEntity<Route> createRoute( @RequestBody Route route) {
        if(userService.isAdmin(userService.getAuthenticatedUser())){
        return new ResponseEntity<>(rService.addRoute(route), HttpStatus.CREATED);}
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @PutMapping("/updateRoute/{routeId}")
    public ResponseEntity<Route> updateIt(@PathVariable("routeId") Long Id, @RequestBody Route route){
        if(userService.isAdmin(userService.getAuthenticatedUser())){
        return new ResponseEntity<>(rService.updateRoute(route, rService.findRouteById(Id)),HttpStatus.ACCEPTED);}
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @DeleteMapping("deleteRoute/{routeId}")
    public ResponseEntity<Route> deleteIt(@PathVariable("routeId") Long Id, @RequestParam(required = false) String key){
        if(userService.isAdmin(userService.getAuthenticatedUser())){
            return new ResponseEntity<>(rService.deleteRoute(Id), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<Route> viewItById(@PathVariable("routeId") Long Id){
        return new ResponseEntity<>(rService.viewRoute(Id), HttpStatus.FOUND);

    }


    @GetMapping("routes")
    public ResponseEntity<List<Route>> viewAll(){
        return new ResponseEntity<>(rService.viewAllRoute(), HttpStatus.OK);
    }
    @PutMapping("/{routeId}/assign-bus/{busId}")
    public void assignBusToRoute(@PathVariable Long routeId, @PathVariable Long busId){
        if (userService.isAdmin(userService.getAuthenticatedUser())) {
            rService.assignBusToRoute(routeId, busId);
        }
    }

    @PutMapping("/{routeId}/assign-driver/{driverId}")
    public void assignDriverToRoute(@PathVariable Long routeId, @PathVariable Long driverId) {
        if (userService.isAdmin(userService.getAuthenticatedUser())) {
            driverService.assignDriverToRoute(routeId, driverId);
        }
    }

    @PostMapping("/{routeId}/reserve")
    public ResponseEntity<Ticket> reserveTicket(@PathVariable Long routeId){
        Ticket ticket = ticketService.reserveTicket(userService.getAuthenticatedUser(), routeId);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/searchRoutes")
    public List<Route> searchRoutes(
            @RequestParam(required = false) String routeFrom,
            @RequestParam(required = false) String routeTo,
            @RequestParam(required = false) LocalDateTime departureTime,
            @RequestParam(required = false) LocalDateTime arrivalTime
    ) {
        return rService.searchRoutes(routeFrom, routeTo, departureTime, arrivalTime);
    }
}
