package org.aovsa.tinyurl.Controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.aovsa.tinyurl.Exceptions.TinyURLNotFoundException;
import org.aovsa.tinyurl.Requests.BatchCreateRequest;
import org.aovsa.tinyurl.Requests.CreateRequest;
import org.aovsa.tinyurl.Responses.BatchCreateResponse;
import org.aovsa.tinyurl.Responses.CreateResponse;
import org.aovsa.tinyurl.Services.TinyURL.TinyURLService;
import org.aovsa.tinyurl.Services.TinyURL.TinyURLServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class TinyURLServiceController {

    private final TinyURLService tinyURLService;

    public TinyURLServiceController(TinyURLServiceImpl tinyURLService) {
        this.tinyURLService = tinyURLService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateResponse> createTinyURL(@RequestBody CreateRequest request) {
        return tinyURLService.createTinyURL(request);
    }

    @PostMapping("/batch/")
    public ResponseEntity<BatchCreateResponse> createBatchTinyURL(@RequestBody BatchCreateRequest request) {
        return tinyURLService.createBatchTinyURL(request);
    }

    @GetMapping("/{id}")
    public void redirectToOriginalURL(@PathVariable String id, HttpServletResponse response) {
        try {
            String originalURL = tinyURLService.redirectTinyURL(id);
            if (originalURL != null && !originalURL.isEmpty()) {
                response.sendRedirect(originalURL);
            } else {
                throw new TinyURLNotFoundException("Tiny URL not found");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
