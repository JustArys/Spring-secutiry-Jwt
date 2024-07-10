package com.example.busdemoarystanbek.controller;

import com.example.busdemoarystanbek.model.DriverSchedule;
import com.example.busdemoarystanbek.service.DriverScheduleService;
import com.example.busdemoarystanbek.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class DriverScheduleController {
    private final DriverScheduleService scheduleService;
    private final UserService userService;

    @PostMapping("/addSchedule")
    public ResponseEntity<DriverSchedule> createSchedule(@RequestBody DriverSchedule schedule){
        if (userService.isAdmin(userService.getAuthenticatedUser())) {
            return new ResponseEntity<>(scheduleService.addSchedule(schedule), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/updateSchedule/{id}")
    public ResponseEntity<DriverSchedule> updateSchedule(@PathVariable("id") Long id, @RequestBody DriverSchedule schedule){
        if (userService.isAdmin(userService.getAuthenticatedUser())) {
            schedule.setId(id);
            return new ResponseEntity<>(scheduleService.updateSchedule(schedule), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/deleteSchedule/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable("id") Long id){
        if (userService.isAdmin(userService.getAuthenticatedUser())) {
            scheduleService.deleteSchedule(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<DriverSchedule>> getAllSchedules(){
        return new ResponseEntity<>(scheduleService.viewAllSchedules(), HttpStatus.OK);
    }

    @GetMapping("/schedule/{id}")
    public ResponseEntity<DriverSchedule> getScheduleById(@PathVariable("id") Long id){
        return new ResponseEntity<>(scheduleService.viewScheduleById(id), HttpStatus.OK);
    }

    @GetMapping("/schedule/driver/{driverId}")
    public ResponseEntity<List<DriverSchedule>> getScheduleByDriverId(@PathVariable("driverId") Long driverId){
        return new ResponseEntity<>(scheduleService.viewScheduleByDriverId(driverId), HttpStatus.OK);
    }
}
