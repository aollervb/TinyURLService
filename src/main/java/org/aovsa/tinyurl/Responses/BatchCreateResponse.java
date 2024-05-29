package org.aovsa.tinyurl.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aovsa.tinyurl.Models.URLPair;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchCreateResponse {
    List<URLPair> urlPairList;
}
