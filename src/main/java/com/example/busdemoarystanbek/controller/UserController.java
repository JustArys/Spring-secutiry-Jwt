package com.example.busdemoarystanbek.controller;

import com.example.busdemoarystanbek.model.UserInfo;
import com.example.busdemoarystanbek.service.TicketService;
import com.example.busdemoarystanbek.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TicketService ticketService;


    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserInfo userInfo) {
        var user = userService.updateUser(userInfo, userService.getAuthenticatedUser());
        return ResponseEntity.ok(user);
    }
    @GetMapping("/authenticated")
    public ResponseEntity<?> getAuthenticatedUserId() {
        return ResponseEntity.ok(userService.getAuthenticatedUser().getId());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @RequestMapping(value = "/confirmemail", method = {RequestMethod.GET, RequestMethod.POST})
    private ResponseEntity<?> confirmEmail(@RequestParam("token") String token) {
        return userService.confirmEmail(token);
    }

    @GetMapping("/tickets")
    public ResponseEntity<?> getTickets() {
        return ResponseEntity.ok(userService.viewAllTickets(userService.getAuthenticatedUser()));
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<?> getTicket(@PathVariable("id") Long id){
        return ResponseEntity.ok(ticketService.getTicket(userService.getAuthenticatedUser(), id));
    }

    @DeleteMapping("tickets/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable("id") Long id){
        return ResponseEntity.ok(ticketService.deleteTicket(userService.getAuthenticatedUser(),id));
    }
}