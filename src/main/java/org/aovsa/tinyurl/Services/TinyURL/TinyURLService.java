package org.aovsa.tinyurl.Services.TinyURL;

import org.aovsa.tinyurl.Utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface TinyURLService {

    ApiResponse<String> createTinyURL(String originalURL);
    String redirectTinyURL(String tinyURL);

}
