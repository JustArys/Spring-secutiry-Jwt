package com.example.busdemoarystanbek.model;

import jakarta.persistence.*;
import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnore;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Statistics {
    @JsonIgnore
    @Id
    @Column(name = "statistics_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "statistics_ticket_sold_amount", nullable = false)
    private int ticketSoldAmount;

    @Column(name = "statistics_ticket_sold_total", nullable = false)
    private int ticketSoldTotal;

    @JsonIgnore
    @Column(name = "statistics_percentage", nullable = false)
    private double ticketPercentage = calculateTicketSoldPercentage();

    private double calculateTicketSoldPercentage() {
        return (double) this.ticketSoldAmount / this.ticketSoldTotal;
    }
}