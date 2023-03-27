package org.example;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EquationsRepository extends MongoRepository<Equation, String> {

}
