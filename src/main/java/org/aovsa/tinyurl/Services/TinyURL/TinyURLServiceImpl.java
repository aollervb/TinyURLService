package org.aovsa.tinyurl.Services.TinyURL;

import org.aovsa.tinyurl.Utils.ApiResponse;
import org.aovsa.tinyurl.Utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class TinyURLServiceImpl implements TinyURLService {

    @Override
    public ApiResponse<String> createTinyURL(String originalURL) {
        String hash = generateHash(originalURL);
        return new ApiResponse<>(Constants.BASE_TINY_URL + hash, "Tiny URL created successfully", HttpStatus.CREATED);
    }

    @Override
    public String redirectTinyURL(String tinyURL)  {
        return "https://www.google.com/";
    }

    private String generateHash(String originalURL) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(originalURL.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.substring(0, 10); // take first 10 characters as tiny URL
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
