package com.mosttoxa;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.logging.Logger;


@SpringBootTest(classes = { EquationsRepository.class, EquationsService.class })
@ExtendWith(SpringExtension.class)
@Import({ MongoConfig.class })

public class TestPersistance {
    private static final Logger LOG = Logger.getLogger(EquationsService.class.getSimpleName());

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
        assertTrue(repository.count() == 0);
    }

    @Test
    public void testSolveEquation() throws Exception {
        String equation = "3x-3=6";
        es.solve(equation);
        assertTrue(repository.count() != 0);
        assertTrue(repository.findByEquation(equation).getResult().equals("3.0"));


    }

    @Test
    public void testCheckIfRootCorrect() throws Exception {
        String equation = "2x+5=17";
        String root  = "6";
        es.check(equation, root);
        for (Equation e: repository.findByResult(6.0)) {
            if (equation.contains(e.getEquation())) {
                assertTrue(LOG.getName().startsWith("Equation's root is correct"));
            }
        }
    }

    @Test
    public void findEquationsByRoot() throws Exception {
        Equation e1 = new Equation();
        e1.setEquation("2x=4");
        e1.setResult("2");
        repository.insert(e1);
        Equation e2 = new Equation();
        e2.setEquation("3x=6");
        e2.setResult("2");
        repository.insert(e2);
        Equation e3 = new Equation();
        e3.setEquation("2x=8");
        e3.setResult("4");
        repository.insert(e3);
        List<Equation> le = repository.findByResult(Double.valueOf(2.0));
        assertTrue(le.size() == 2);
    }
}