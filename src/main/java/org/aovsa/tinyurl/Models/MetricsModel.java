package org.aovsa.tinyurl.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@AllArgsConstructor
@Document(collection = "metrics")
public class MetricsModel {
    @Id
    private String shortURL;
    private long accessCount;
}
