package org.aovsa.tinyurl.Services.TinyURL;

import org.aovsa.tinyurl.Models.URLPair;
import org.aovsa.tinyurl.Utils.ApiResponse;

import java.util.List;
import java.util.Map;

/**
 * Interface for TinyURL operations.
 */
public interface TinyURLService {

    /**
     * Creates a tiny URL from the given original URL.
     *
     * @param originalURL the original URL to be shortened
     * @return ApiResponse containing the tiny URL and status message
     */
    ApiResponse<String> createTinyURL(String originalURL);

    ApiResponse<List<URLPair>> createBatchTinyURL(List<String> batchOriginalURLs);

    /**
     * Redirects the tiny URL to the original URL.
     *
     * @param tinyURL the tiny URL to be redirected
     * @return the original URL
     */
    String redirectTinyURL(String tinyURL);
}