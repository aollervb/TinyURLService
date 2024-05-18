package org.aovsa.tinyurl.Services.TinyURLMetrics;

import org.aovsa.tinyurl.Exceptions.MetricNotWhitelistedException;
import org.aovsa.tinyurl.Models.MetricDataModel;
import org.aovsa.tinyurl.Models.MetricsModel;
import org.aovsa.tinyurl.Repository.MetricsDataRepository;
import org.aovsa.tinyurl.Repository.MetricsRepository;
import org.aovsa.tinyurl.Utils.ApiResponse;
import org.bson.BsonDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TinyURLMetricsServiceImpl implements TinyURLMetricsService{
    private final MetricsRepository metricsRepository;
    private final MetricsDataRepository metricsDataRepository;

    public TinyURLMetricsServiceImpl(MetricsRepository metricsRepository, MetricsDataRepository metricsDataRepository) {
        this.metricsRepository = metricsRepository;
        this.metricsDataRepository = metricsDataRepository;
    }

    @Override
    public ApiResponse<Map<String, Object>> getAccessCount(String tinyURL, long startDate, long endDate) {

        List<MetricDataModel> metricsData = metricsDataRepository.findAllByShortURL(tinyURL);
        MetricsModel metricsModel = metricsRepository.findByShortURL(tinyURL);

        if (metricsModel != null) {
            return new ApiResponse<>(Map.of("accessCount", metricsModel.getAccessCount(), "metricsData", metricsData), "Access count retrieved successfully", HttpStatus.OK);
        }

        return null;
    }

    @Override
    public void incrementAccessCount(String tinyURL) {
        MetricsModel metricsModel = metricsRepository.findByShortURL(tinyURL);
        try {
            if (metricsModel != null) {
                metricsModel.setAccessCount(metricsModel.getAccessCount() + 1);
                metricsRepository.save(metricsModel);

                MetricDataModel metricDataModel = new MetricDataModel(UUID.randomUUID().toString(), tinyURL, new Date());
                metricsDataRepository.save(metricDataModel);
            } else {
                throw new MetricNotWhitelistedException("Metric for " + tinyURL + " not whitelisted");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponse<Map<String, String>> whitelistMetric(String tinyURL) {

        MetricsModel metricsModel = new MetricsModel(tinyURL, 0);
        metricsRepository.save(metricsModel);

        return new ApiResponse<>(Map.of("metricName", tinyURL, "status", "Metric whitelisted successfully"), "Metric whitelisted successfully", HttpStatus.OK);
    }
}
