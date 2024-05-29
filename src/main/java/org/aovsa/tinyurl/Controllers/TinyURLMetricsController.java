package org.aovsa.tinyurl.Controllers;

import org.aovsa.tinyurl.Models.AggregatedMetricModel;
import org.aovsa.tinyurl.Models.MetricDataModel;
import org.aovsa.tinyurl.Requests.MetricsRequest;
import org.aovsa.tinyurl.Responses.MetricsResponse;
import org.aovsa.tinyurl.Services.TinyURLMetrics.TinyURLMetricsService;
import org.aovsa.tinyurl.Utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/metrics")
public class TinyURLMetricsController {
    private final TinyURLMetricsService tinyURLMetricsService;

    public TinyURLMetricsController(TinyURLMetricsService tinyURLMetricsService) {
        this.tinyURLMetricsService = tinyURLMetricsService;
    }

    @GetMapping("/")
    public ResponseEntity<MetricsResponse> getAccessMetrics(@RequestBody MetricsRequest request) {
        return tinyURLMetricsService.getAccessCount(request);
    }
}
