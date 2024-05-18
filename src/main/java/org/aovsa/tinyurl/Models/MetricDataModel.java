package org.aovsa.tinyurl.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.BsonDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@Document(collection = "metrics_data")
public class MetricDataModel {
    @Id
    private String id;
    private String shortURL;
    private Date accessTime;
}
