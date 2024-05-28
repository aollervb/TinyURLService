package org.aovsa.tinyurl.Repository;

import org.aovsa.tinyurl.Models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserModel, String>{
    Optional<UserModel> findByEmail(String email);

    Boolean existsByEmail(String email);
}
