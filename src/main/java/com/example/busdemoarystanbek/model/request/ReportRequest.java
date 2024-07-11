package com.example.busdemoarystanbek.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequest {
    private String reportDescription;
    private Long statisticsId;
    private Long analyticsId;
}
