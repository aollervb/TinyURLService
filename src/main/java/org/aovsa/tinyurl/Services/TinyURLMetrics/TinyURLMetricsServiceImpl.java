package org.aovsa.tinyurl.Services.TinyURLMetrics;

import org.aovsa.tinyurl.Exceptions.MetricNotWhitelistedException;
import org.aovsa.tinyurl.Models.AggregatedMetricModel;
import org.aovsa.tinyurl.Models.MetricDataModel;
import org.aovsa.tinyurl.Models.MetricsModel;
import org.aovsa.tinyurl.Repository.MetricsDataRepository;
import org.aovsa.tinyurl.Repository.MetricsRepository;
import org.aovsa.tinyurl.Requests.MetricsRequest;
import org.aovsa.tinyurl.Responses.MetricsResponse;
import org.aovsa.tinyurl.Utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.DateFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TinyURLMetricsServiceImpl implements TinyURLMetricsService{
    private final MetricsRepository metricsRepository;
    private final MetricsDataRepository metricsDataRepository;

    public TinyURLMetricsServiceImpl(MetricsRepository metricsRepository, MetricsDataRepository metricsDataRepository) {
        this.metricsRepository = metricsRepository;
        this.metricsDataRepository = metricsDataRepository;
    }

    @Override
    public ResponseEntity<MetricsResponse> getAccessCount(MetricsRequest metricsRequest) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        try {
            MetricsResponse response = new MetricsResponse();
            Date startDate = formatter.parse(metricsRequest.getStartDate());
            Date endDate = formatter.parse(metricsRequest.getEndDate());
            long interval = metricsRequest.getGranularity();
            String tinyURL = metricsRequest.getTinyURL();
            List<AggregatedMetricModel> metricDataModel = aggregateMetricsInTimeRange(startDate, endDate, interval, tinyURL);

            if (metricDataModel.equals(new ArrayList<AggregatedMetricModel>())) {
                return ResponseEntity.badRequest().body(new MetricsResponse());
            }

            return ResponseEntity.ok(new MetricsResponse(tinyURL, metricDataModel));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void incrementAccessCount(String tinyURL) {
        addCount(tinyURL);
    }

    private void addCount(String tinyURL) {
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

    private List<AggregatedMetricModel> aggregateMetricsInTimeRange(Date startDate, Date endDate, long interval, String shortURL) {
        List<MetricDataModel> metrics = metricsDataRepository.findAllByShortURLAndDate(shortURL, startDate, endDate);
        return getAggregatedMetricModels(startDate, endDate, interval, metrics);
    }

    private static List<AggregatedMetricModel> getAggregatedMetricModels(Date startDate, Date endDate, long interval, List<MetricDataModel> metrics) {
        List<AggregatedMetricModel> aggregatedMetricModels = new ArrayList<>();
        Date newEndDate = new Date(startDate.getTime());
        Date newStartDate = new Date(startDate.getTime());

        while (newEndDate.before(endDate)) {
            newEndDate.setTime(newStartDate.getTime() + TimeUnit.MINUTES.toMillis(interval));
            AggregatedMetricModel aggregatedMetricModel = new AggregatedMetricModel();

            for (MetricDataModel metric : metrics) {
                if (newEndDate.after(metric.getAccessTime()) && newStartDate.before(metric.getAccessTime())) {
                    aggregatedMetricModel.setAccessCount(aggregatedMetricModel.getAccessCount() + 1);
                    aggregatedMetricModel.setGraphTime(new Date(newEndDate.getTime()));
                }
            }

            if (aggregatedMetricModel.getAccessCount() > 0) {
                aggregatedMetricModels.add(aggregatedMetricModel);
            }

            newStartDate.setTime(newEndDate.getTime());
        }

        return aggregatedMetricModels;
    }
}
