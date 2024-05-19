package org.aovsa.tinyurl.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class AggregatedMetricModel {
    private long accessCount;
    private Date graphTime;
}
