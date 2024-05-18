package org.aovsa.tinyurl.Repository;

import org.aovsa.tinyurl.Models.MetricDataModel;
import org.bson.BsonDateTime;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MetricsDataRepository extends MongoRepository<MetricDataModel, String> {
    List<MetricDataModel> findAllByShortURL(String shortURL);
}
