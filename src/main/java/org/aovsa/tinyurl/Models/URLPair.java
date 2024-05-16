package org.aovsa.tinyurl.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "url_pairs")
public class URLPair {
    @Id
    private String shortURL;
    private String originalURL;
}
