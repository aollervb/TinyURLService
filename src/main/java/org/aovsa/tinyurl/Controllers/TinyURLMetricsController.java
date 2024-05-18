package org.aovsa.tinyurl.Controllers;

import org.aovsa.tinyurl.Services.TinyURLMetrics.TinyURLMetricsService;
import org.aovsa.tinyurl.Utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Map;

@RestController
@RequestMapping("/metrics")
public class TinyURLMetricsController {
    private final TinyURLMetricsService tinyURLMetricsService;

    public TinyURLMetricsController(TinyURLMetricsService tinyURLMetricsService) {
        this.tinyURLMetricsService = tinyURLMetricsService;
    }

    @GetMapping("/")
    public ApiResponse<Map<String, Object>> getAccessMetrics(@RequestBody Map<String, String> request) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        try {
            long longStartDate = formatter.parse(request.get("startDate")).getTime();
            long longEndDate = formatter.parse(request.get("endDate")).getTime();
            return tinyURLMetricsService.getAccessCount(request.get("tinyURL"), longStartDate, longEndDate);
        } catch (Exception e) {
            return new ApiResponse<>(null, "Invalid date format", HttpStatus.BAD_REQUEST);
        }


    }
}
