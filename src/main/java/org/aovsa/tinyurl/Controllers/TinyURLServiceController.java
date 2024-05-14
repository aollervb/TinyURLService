package org.aovsa.tinyurl.Controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.aovsa.tinyurl.Services.TinyURL.TinyURLServiceImpl;
import org.aovsa.tinyurl.Utils.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class TinyURLServiceController {

    private final TinyURLServiceImpl tinyURLService;

    public TinyURLServiceController(TinyURLServiceImpl tinyURLService) {
        this.tinyURLService = tinyURLService;
    }

    @PostMapping("/")
    public ApiResponse<String> createTinyURL(@RequestBody String originalURL) {
        return tinyURLService.createTinyURL(originalURL);
    }

    @GetMapping("/{id}")
    public void redirectToOriginalURL(@PathVariable String id, HttpServletResponse response) {
        try {
            response.sendRedirect(tinyURLService.redirectTinyURL(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
