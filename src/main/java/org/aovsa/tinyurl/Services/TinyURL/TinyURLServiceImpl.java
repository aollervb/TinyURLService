package org.aovsa.tinyurl.Services.TinyURL;

import org.aovsa.tinyurl.Models.URLPair;
import org.aovsa.tinyurl.Repository.URLPairRepository;
import org.aovsa.tinyurl.Requests.BatchCreateRequest;
import org.aovsa.tinyurl.Requests.CreateRequest;
import org.aovsa.tinyurl.Responses.BatchCreateResponse;
import org.aovsa.tinyurl.Responses.CreateResponse;
import org.aovsa.tinyurl.Services.TinyURLMetrics.TinyURLMetricsService;
import org.aovsa.tinyurl.Services.TinyURLMetrics.TinyURLMetricsServiceImpl;
import org.aovsa.tinyurl.Utils.ApiResponse;
import org.aovsa.tinyurl.Utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CreateResponse> createTinyURL(CreateRequest request) {
        try {
            assert request.getOriginalUrl() != null;
            URLPair urlPair = createTinyURLImpl(request.getOriginalUrl());
            urlPairRepository.save(urlPair);
            return ResponseEntity.ok(new CreateResponse(urlPair));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CreateResponse(null));
        }
    }

    @Override
    public ResponseEntity<BatchCreateResponse> createBatchTinyURL(BatchCreateRequest request) {
        List<URLPair> createdURLPairs = new ArrayList<>();
        for (String originalURL : request.getOriginalUrls()) {
            URLPair urlPair = createTinyURLImpl(originalURL);
            createdURLPairs.add(urlPair);
        }
        return ResponseEntity.ok(new BatchCreateResponse(createdURLPairs));
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
        urlPair.setShortURL(Constants.BASE_TINY_URL + urlPair.getShortURL());
        return urlPair;
    }
}
