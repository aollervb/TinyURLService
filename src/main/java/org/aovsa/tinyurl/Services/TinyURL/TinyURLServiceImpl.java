package org.aovsa.tinyurl.Services.TinyURL;

import org.aovsa.tinyurl.Models.URLPair;
import org.aovsa.tinyurl.Repository.URLPairRepository;
import org.aovsa.tinyurl.Services.TinyURLMetrics.TinyURLMetricsService;
import org.aovsa.tinyurl.Services.TinyURLMetrics.TinyURLMetricsServiceImpl;
import org.aovsa.tinyurl.Utils.ApiResponse;
import org.aovsa.tinyurl.Utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TinyURLServiceImpl implements TinyURLService {

    private final URLPairRepository urlPairRepository;
    private final TinyURLMetricsService tinyURLMetricsService;

    public TinyURLServiceImpl(URLPairRepository urlPairRepository, TinyURLMetricsServiceImpl tinyURLMetricsServiceImpl) {
        this.urlPairRepository = urlPairRepository;
        this.tinyURLMetricsService = tinyURLMetricsServiceImpl;
    }

    @Override
    public ApiResponse<String> createTinyURL(String originalURL) {
        try {
            URLPair urlPair = createTinyURLImpl(originalURL);
            return new ApiResponse<>(Constants.BASE_TINY_URL + urlPair.getShortURL(), "Tiny URL created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ApiResponse<>(null, "Tiny URL creation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<List<URLPair>> createBatchTinyURL(List<String> batchOriginalURLs) {
        List<URLPair> createdURLPairs = new ArrayList<>();
        for (String originalURL : batchOriginalURLs) {
            URLPair urlPair = createTinyURLImpl(originalURL);
            urlPair.setShortURL(Constants.BASE_TINY_URL + urlPair.getShortURL());
            createdURLPairs.add(urlPair);
        }
        return new ApiResponse<>(createdURLPairs, "Tiny URLs created successfully", HttpStatus.CREATED);
    }

    @Override
    public String redirectTinyURL(String tinyURL)  {
        URLPair redirectPath = urlPairRepository.findByShortURL(tinyURL);
        if (redirectPath != null) {
            tinyURLMetricsService.incrementAccessCount(tinyURL);
            return redirectPath.getOriginalURL();
        }
        return "";
    }

    private String generateUUID() {
        return UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0,10);
    }

    private URLPair createURLPair(String originalURL) {
        return new URLPair(generateUUID(), originalURL);
    }

    private URLPair createTinyURLImpl(String originalURL) {
        URLPair urlPair = createURLPair(originalURL);
        urlPairRepository.save(urlPair);
        tinyURLMetricsService.whitelistMetric(urlPair.getShortURL());
        return urlPair;
    }
}
