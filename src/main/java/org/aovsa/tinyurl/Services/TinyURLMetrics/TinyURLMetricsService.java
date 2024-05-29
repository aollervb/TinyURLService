package org.aovsa.tinyurl.Services.TinyURLMetrics;

import org.aovsa.tinyurl.Models.AggregatedMetricModel;
import org.aovsa.tinyurl.Models.MetricDataModel;
import org.aovsa.tinyurl.Requests.MetricsRequest;
import org.aovsa.tinyurl.Responses.MetricsResponse;
import org.aovsa.tinyurl.Utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TinyURLMetricsService {

    /**
     * Returns the number of times the tiny URL has been accessed.
     *
     * @param metricsRequest Request containing, startDate, endDate, granularity and tinyURL
     * @return the number of times the tiny URL has been accessed
     */
    ResponseEntity<MetricsResponse> getAccessCount(MetricsRequest metricsRequest);

    /**
     * Increments the access count of the tiny URL by 1.
     *
     * @param tinyURL the tiny URL to be updated
     */
    void incrementAccessCount(String tinyURL);

    /**
     * Whitelist a metric to be able to start collecting data.
     *
     * @param metricName the metric to be whitelisted
     * @return ApiResponse containing the metric name and status message
     */
    ApiResponse<Map<String, String>> whitelistMetric(String metricName);
}
