package org.aovsa.tinyurl.Services.TinyURL;

import org.aovsa.tinyurl.Models.URLPair;
import org.aovsa.tinyurl.Repository.URLPairRepository;
import org.aovsa.tinyurl.Requests.BatchCreateRequest;
import org.aovsa.tinyurl.Requests.CreateRequest;
import org.aovsa.tinyurl.Responses.BatchCreateResponse;
import org.aovsa.tinyurl.Responses.CreateResponse;
import org.aovsa.tinyurl.Services.TinyURLMetrics.TinyURLMetricsServiceImpl;
import org.aovsa.tinyurl.Utils.ApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TinyURLServiceImplTests {

    @Mock
    private URLPairRepository urlPairRepository;

    @Mock
    private TinyURLMetricsServiceImpl tinyURLMetricsServiceImpl;

    @InjectMocks
    private TinyURLServiceImpl tinyURLServiceImpl;

    @Test
    public void testCreateTinyURL() {
        String originalURL = "http://example.com";
        URLPair urlPair = new URLPair("1234567890", originalURL);
        when(urlPairRepository.save(any(URLPair.class))).thenReturn(urlPair);

        CreateRequest request = new CreateRequest();
        request.setOriginalUrl(originalURL);

        ResponseEntity<CreateResponse> response = tinyURLServiceImpl.createTinyURL(request);

        assertNotNull(Objects.requireNonNull(response.getBody()).getUrlPair());

        verify(urlPairRepository, times(1)).save(any(URLPair.class));
        verify(tinyURLMetricsServiceImpl, times(1)).whitelistMetric(anyString());
    }

    @Test
    public void testCreateBatchTinyURL() {
        List<String> originalURLs = Arrays.asList("http://example1.com", "http://example2.com");
        URLPair urlPair1 = new URLPair("1234567890", originalURLs.get(0));
        URLPair urlPair2 = new URLPair("0987654321", originalURLs.get(1));
        when(urlPairRepository.save(any(URLPair.class))).thenReturn(urlPair1, urlPair2);

        BatchCreateRequest request = new BatchCreateRequest();
        request.setOriginalUrls(originalURLs);

        ResponseEntity<BatchCreateResponse> response = tinyURLServiceImpl.createBatchTinyURL(request);

        assertEquals(2, Objects.requireNonNull(response.getBody()).getUrlPairList().size());

        verify(urlPairRepository, times(2)).save(any(URLPair.class));
        verify(tinyURLMetricsServiceImpl, times(2)).whitelistMetric(anyString());
    }

    @Test
    public void testRedirectTinyURL() {
        String tinyURL = "1234567890";
        URLPair urlPair = new URLPair(tinyURL, "http://example.com");
        when(urlPairRepository.findByShortURL(tinyURL)).thenReturn(urlPair);

        String redirectURL = tinyURLServiceImpl.redirectTinyURL(tinyURL);

        assertEquals(urlPair.getOriginalURL(), redirectURL);

        verify(urlPairRepository, times(1)).findByShortURL(tinyURL);
        verify(tinyURLMetricsServiceImpl, times(1)).incrementAccessCount(tinyURL);
    }
}