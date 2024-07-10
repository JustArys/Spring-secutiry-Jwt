package com.example.busdemoarystanbek.controller;

import com.example.busdemoarystanbek.model.Driver;
import com.example.busdemoarystanbek.service.DriverService;
import com.example.busdemoarystanbek.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/driver")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;
    private final UserService userService;

    @PostMapping("/addDriver")
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver) {
        if (userService.isAdmin(userService.getAuthenticatedUser())) {
            return new ResponseEntity<>(driverService.addDriver(driver), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/updateDriver/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable("id") Long id, @RequestBody Driver driver){
        if (userService.isAdmin(userService.getAuthenticatedUser())) {
            driver.setId(id);
            return new ResponseEntity<>(driverService.updateDriver(driver), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/deleteDriver/{id}")
    public ResponseEntity<Driver> deleteDriver(@PathVariable("id") Long id) {
        if (userService.isAdmin(userService.getAuthenticatedUser())) {
            driverService.deleteDriver(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    @GetMapping("/drivers")
    public ResponseEntity<List<Driver>> viewAll(){
        return new ResponseEntity<>(driverService.getAllDrivers(), HttpStatus.OK);
    }

    @GetMapping("/driver/{id}")
    public ResponseEntity<Driver> viewDriverById(@PathVariable("id") Long id){
        return new ResponseEntity<>(driverService.getDriverById(id), HttpStatus.OK);
    }
}
