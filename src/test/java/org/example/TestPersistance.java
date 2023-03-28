package org.example;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest(classes = { EquationsRepository.class })
@ExtendWith(SpringExtension.class)
@Import({ MongoConfig.class })

public class TestPersistance {

    @Autowired
    EquationsRepository repository;

    @Test
    public void tesCreateAndGet() throws Exception {
        Equation e = new Equation();
        e.setEquation("ghghgh");
        e.setResult("445");
        repository.insert(e);
        assertTrue(repository.count()==1);
        repository.deleteAll();
    }
}