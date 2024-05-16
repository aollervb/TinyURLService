package org.aovsa.tinyurl.Repository;

import org.aovsa.tinyurl.Models.URLPair;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface URLPairRepository extends MongoRepository<URLPair, String> {
    URLPair findByShortURL(String shortURL);
}
