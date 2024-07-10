package com.example.busdemoarystanbek.controller;

import com.example.busdemoarystanbek.model.Report;
import com.example.busdemoarystanbek.model.request.ReportRequest;
import com.example.busdemoarystanbek.service.ReportService;
import com.example.busdemoarystanbek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @PostMapping("/sales")
    public ResponseEntity<Report> getTicketSalesReport(@RequestBody ReportRequest reportRequest) {
        if (userService.isAdmin(userService.getAuthenticatedUser())) {
            Report report = reportService.getTicketSalesReport(reportRequest.getStartDate(), reportRequest.getEndDate());
            return new ResponseEntity<>(report, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/load")
    public ResponseEntity<Report> getFlightLoadAnalytics(@RequestBody ReportRequest reportRequest) {
        if (userService.isAdmin(userService.getAuthenticatedUser())) {
            Report report = reportService.getFlightLoadAnalytics(reportRequest.getStartDate(), reportRequest.getEndDate());
            return new ResponseEntity<>(report, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/stats")
    public ResponseEntity<Report> getPassengerAndFlightStatistics(@RequestBody ReportRequest reportRequest) {
        if (userService.isAdmin(userService.getAuthenticatedUser())) {
            Report report = reportService.getPassengerAndFlightStatistics(reportRequest.getStartDate(), reportRequest.getEndDate());
            return new ResponseEntity<>(report, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
