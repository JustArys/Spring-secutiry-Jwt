package com.example.busdemoarystanbek.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Ticket")
public class Ticket {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ticket_id")
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Route route;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "booking_time")
    private LocalDateTime bookingTime;

    public LocalDateTime ticketBetweenDate(LocalDateTime start, LocalDateTime end) {
        if (bookingTime != null && bookingTime.isAfter(start) && bookingTime.isBefore(end)) {
            return bookingTime;
        } else {
            return null;
        }
    }
}
