package com.example.busdemoarystanbek.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequest {
    LocalDateTime startDate;
    LocalDateTime endDate;
}
