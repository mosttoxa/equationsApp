package com.mosttoxa;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EquationsRepository extends MongoRepository<Equation, String> {
    public List<Equation> findByResult(Double result);
    public Equation findByEquation(String equation);
}
