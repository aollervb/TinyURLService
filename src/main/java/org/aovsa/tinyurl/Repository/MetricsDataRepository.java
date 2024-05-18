package org.aovsa.tinyurl.Repository;

import org.aovsa.tinyurl.Models.MetricDataModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface MetricsDataRepository extends MongoRepository<MetricDataModel, String> {
    List<MetricDataModel> findAllByShortURL(String shortURL);

    @Query("{ 'shortURL' : ?0, 'accessTime' : { $gte : ?1 , $lte : ?2} }")
    List<MetricDataModel> findAllByShortURLAndDate(String shortURL, Date startDate, Date endDate);
}
