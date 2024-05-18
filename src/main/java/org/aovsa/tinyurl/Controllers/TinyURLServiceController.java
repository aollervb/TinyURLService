package org.aovsa.tinyurl.Controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.aovsa.tinyurl.Exceptions.TinyURLNotFoundException;
import org.aovsa.tinyurl.Services.TinyURL.TinyURLService;
import org.aovsa.tinyurl.Services.TinyURL.TinyURLServiceImpl;
import org.aovsa.tinyurl.Utils.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("")
public class TinyURLServiceController {

    private final TinyURLService tinyURLService;

    public TinyURLServiceController(TinyURLServiceImpl tinyURLService) {
        this.tinyURLService = tinyURLService;
    }

    @PostMapping("/")
    public ApiResponse<String> createTinyURL(@RequestBody Map<String, String> originalURL) {
        return tinyURLService.createTinyURL(originalURL.get("originalURL"));
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
