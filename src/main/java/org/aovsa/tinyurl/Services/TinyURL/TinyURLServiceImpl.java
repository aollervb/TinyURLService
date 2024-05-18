package org.aovsa.tinyurl.Services.TinyURL;

import org.aovsa.tinyurl.Models.URLPair;
import org.aovsa.tinyurl.Repository.URLPairRepository;
import org.aovsa.tinyurl.Services.TinyURLMetrics.TinyURLMetricsService;
import org.aovsa.tinyurl.Services.TinyURLMetrics.TinyURLMetricsServiceImpl;
import org.aovsa.tinyurl.Utils.ApiResponse;
import org.aovsa.tinyurl.Utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
        String shortURL = generateUUID();

        try {
            URLPair newURLPair = new URLPair(shortURL, originalURL);
            URLPair existingURLPair = urlPairRepository.findByShortURL(shortURL);

            if (existingURLPair != null) {
                return new ApiResponse<>(existingURLPair.getOriginalURL(), "Tiny URL already exists", HttpStatus.CONFLICT);
            } else {
                urlPairRepository.save(newURLPair);
                tinyURLMetricsService.whitelistMetric(newURLPair.getShortURL());
            }

        } catch (Exception e) {
            return new ApiResponse<>(null, "Tiny URL creation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ApiResponse<>(Constants.BASE_TINY_URL + shortURL, "Tiny URL created successfully", HttpStatus.CREATED);
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
}
