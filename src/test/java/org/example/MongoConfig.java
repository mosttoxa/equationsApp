package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String MONGO_DB_URI;
    @Value("${mongo.db.name}")
    private String MONGO_DB_NAME;

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoClient mongoClient = MongoClients.create(MONGO_DB_URI);
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, MONGO_DB_NAME);
        return mongoTemplate;
    }
}