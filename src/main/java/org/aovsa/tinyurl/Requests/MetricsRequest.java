package org.aovsa.tinyurl.Requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MetricsRequest {

    private String tinyURL;
    private String startDate;
    private String endDate;
    private long granularity;

}
