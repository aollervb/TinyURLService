package org.aovsa.tinyurl.Services.TinyURL;

import org.aovsa.tinyurl.Utils.ApiResponse;

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

    /**
     * Redirects the tiny URL to the original URL.
     *
     * @param tinyURL the tiny URL to be redirected
     * @return the original URL
     */
    String redirectTinyURL(String tinyURL);
}