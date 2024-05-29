package org.aovsa.tinyurl.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aovsa.tinyurl.Models.AggregatedMetricModel;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricsResponse {
    private String tinyURL;
    private List<AggregatedMetricModel> metricsData;
}
