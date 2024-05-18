package org.aovsa.tinyurl.Repository;

import org.aovsa.tinyurl.Models.MetricsModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetricsRepository extends MongoRepository<MetricsModel, String> {
    MetricsModel findByShortURL(String shortURL);
}
