package com.example.busdemoarystanbek.controller;

import com.example.busdemoarystanbek.model.Route;
import com.example.busdemoarystanbek.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/route")
@RequiredArgsConstructor
public class RouteController {
    private RouteService rService;

    @PostMapping("/addRoute")
    public ResponseEntity<Route> createRoute( @RequestBody Route route) {
        return new ResponseEntity<Route>(rService.addRoute(route), HttpStatus.CREATED);

    }

    @PutMapping("/updateRoute")
    public ResponseEntity<Route> updateIt( @RequestBody Route route){
        return new ResponseEntity<Route>(rService.updateRoute(route),HttpStatus.ACCEPTED);

    }

    @DeleteMapping("deleteRoute/{routeId}")
    public ResponseEntity<Route> deleteIt(@PathVariable("routeId") Long Id, @RequestParam(required = false) String key){
        return new ResponseEntity<Route>(rService.deleteRoute(Id), HttpStatus.OK);
    }

    @GetMapping("route/{routeId}")
    public ResponseEntity<Route> viewItbyId(@PathVariable("routeId") Long Id){
        return new ResponseEntity<Route>(rService.viewRoute(Id), HttpStatus.FOUND);

    }


    @GetMapping("routes")
    public ResponseEntity<List<Route>> viewAll(){
        return new ResponseEntity<>(rService.viewAllRoute(), HttpStatus.OK);

    }
}
