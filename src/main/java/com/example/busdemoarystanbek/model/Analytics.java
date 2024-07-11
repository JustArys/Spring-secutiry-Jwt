package com.example.busdemoarystanbek.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Analytics {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "analytics_id")
    private Long id;

    @Column(name = "analytics_trips_amount", nullable = false)
    private int tripsAmount;

    @Column(name = "analytics_tickets_sold", nullable = false)
    private int ticketsSold;

    @JsonIgnore
    @Column(name = "analytics_tickets_sold_percentage", nullable = false)
    private double ticketsSoldPercentage;

    @JsonIgnore
    @ManyToOne
    private Bus bus;

    public void setBus(Bus bus) {
        this.bus = bus;
        this.ticketsSoldPercentage = calculateTicketsSoldPercentage();
    }

    public double calculateTicketsSoldPercentage() {
        if (bus != null && bus.getSeats() > 0) {
            return ((double) ticketsSold / bus.getSeats()) * 100;
        } else {
            return 0.0;
        }
    }
}
