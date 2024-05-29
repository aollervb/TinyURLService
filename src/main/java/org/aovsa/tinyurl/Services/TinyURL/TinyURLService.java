package org.aovsa.tinyurl.Services.TinyURL;

import org.aovsa.tinyurl.Models.URLPair;
import org.aovsa.tinyurl.Requests.BatchCreateRequest;
import org.aovsa.tinyurl.Requests.CreateRequest;
import org.aovsa.tinyurl.Responses.BatchCreateResponse;
import org.aovsa.tinyurl.Responses.CreateResponse;
import org.aovsa.tinyurl.Utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * Interface for TinyURL operations.
 */
public interface TinyURLService {

    /**
     * Creates a tiny URL from the given original URL.
     *
     * @param request of type {@link CreateRequest} containing the original URL
     * @return {@link CreateResponse} containing the urlPair, with shortUrl and originalUrl, and status message
     */
    ResponseEntity<CreateResponse> createTinyURL(CreateRequest request);

    /**
     * Creates n tiny URLs from the given original URLs.
     *
     * @param request of type {@link BatchCreateRequest} containing the original URLs
     * @return ApiResponse containing the tiny URL and status message
     */
    ResponseEntity<BatchCreateResponse> createBatchTinyURL(BatchCreateRequest request);

    /**
     * Redirects the tiny URL to the original URL.
     *
     * @param tinyURL the tiny URL to be redirected
     * @return the original URL
     */
    String redirectTinyURL(String tinyURL);
}