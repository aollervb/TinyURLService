package org.aovsa.tinyurl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class TinyUrlApplication {
    public static void main(String[] args) {
        SpringApplication.run(TinyUrlApplication.class, args);
    }
}
