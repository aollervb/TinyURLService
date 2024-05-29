package org.aovsa.tinyurl.Requests;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateRequest {
    private String originalUrl;
}
