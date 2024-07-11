package com.example.busdemoarystanbek.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Report {
    @JsonIgnore
    @Id
    @Column(name = "report_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Statistics statistics;

    @JsonIgnore
    @ManyToOne
    private Analytics analytics;

    @Column(name = "report_description")
    private String reportDescription;
}
