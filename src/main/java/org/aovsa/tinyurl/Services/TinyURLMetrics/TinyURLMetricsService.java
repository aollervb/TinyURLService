package org.aovsa.tinyurl.Services.TinyURLMetrics;

import org.aovsa.tinyurl.Utils.ApiResponse;

import java.util.Date;
import java.util.Map;

public interface TinyURLMetricsService {

    /**
     * Returns the number of times the tiny URL has been accessed.
     *
     * @param tinyURL the tiny URL to be checked
     * @return the number of times the tiny URL has been accessed
     */
    ApiResponse<Map<String, Object>> getAccessCount(String tinyURL , long startDate, long endDate);

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