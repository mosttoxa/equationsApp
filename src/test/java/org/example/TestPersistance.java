package org.example;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest(classes = { EquationsRepository.class, EquationsService.class })
@ExtendWith(SpringExtension.class)
@Import({ MongoConfig.class })

public class TestPersistance {

    @Autowired
    EquationsRepository repository;

    @Autowired
    EquationsService es;

    @Test
    public void tesCreateAndGet() throws Exception {
        Equation e = new Equation();
        e.setEquation("ghghgh");
        e.setResult("445");
        repository.insert(e);
        assertTrue(repository.count()==1);
        repository.deleteAll();
    }

    @Test
    public void testWrongInput() throws Exception {
        String input = "2x*/4=2";
        es.solve(input);
        assertTrue(repository.count()==0);
    }

    @Test
    public void testSolveEquation() throws Exception {
        String equation = "solve 2*x+5=17";
        es.solve(equation);
        for (Equation e: repository.findByResult("6.0")) {
          if (equation.contains(e.getEquation())) {
              assertTrue(true);
            }
        }
    }

}