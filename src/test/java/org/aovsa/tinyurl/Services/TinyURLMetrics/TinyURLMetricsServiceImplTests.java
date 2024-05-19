package org.aovsa.tinyurl.Services.TinyURLMetrics;

import org.aovsa.tinyurl.Models.URLPair;
import org.aovsa.tinyurl.Repository.MetricsRepository;
import org.aovsa.tinyurl.Repository.URLPairRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TinyURLMetricsServiceImplTests {

    @Mock
    private MetricsRepository metricsRepository;
    @InjectMocks
    private TinyURLMetricsServiceImpl tinyURLMetricsServiceImpl;

    @Test
    public void testWhitelistMetric() {
        String tinyURL = "1234567890";
        URLPair urlPair = new URLPair(tinyURL, "http://example.com");

        tinyURLMetricsServiceImpl.whitelistMetric(tinyURL);
        verify(metricsRepository, times(1)).save(any());
    }
}
