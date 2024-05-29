package org.aovsa.tinyurl.Requests;

import lombok.Data;

import java.util.List;

@Data
public class BatchCreateRequest {
    List<String> originalUrls;

}
