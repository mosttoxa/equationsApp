package org.example;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EquationsRepository extends MongoRepository<Equation, String> {
    public List<Equation> findByResult(String result);
}
