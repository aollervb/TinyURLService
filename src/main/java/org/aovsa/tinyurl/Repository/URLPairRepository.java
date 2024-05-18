package org.aovsa.tinyurl.Repository;

import org.aovsa.tinyurl.Models.URLPair;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface URLPairRepository extends MongoRepository<URLPair, String> {
    URLPair findByShortURL(String shortURL);
}
