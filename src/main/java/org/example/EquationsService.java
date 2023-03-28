package org.example;

import org.mariuszgromada.math.mxparser.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.logging.Logger;

@Service
public class EquationsService {
    private static final Logger LOG = Logger.getLogger(EquationsService.class.getSimpleName());
    @Autowired
    EquationsRepository repo;

    public void service(String input) {
        Equation e = new Equation();
        e.setEquation(input);
        String result = solveEquation(input);
        if (!result.equals("NaN")) {
            e.setResult(result);
            repo.insert(e);
        } else {
            LOG.warning("Equation syntax is not valid");
        }
    }

    // Method to solve the given equation
    public static String solveEquation(String equation) {
        //bring the equation to the form f(x) = 0 to use mXparser calculate method
        String[] sides = equation.split("=");
        String eq = sides[0] + "-" + sides[1];
        Expression e = new Expression("solve(" + eq + ", x," + Integer.MIN_VALUE + "," + Integer.MAX_VALUE + ")");
        e.setVerboseMode();
        double x = e.calculate();

        return String.valueOf(x);
    }

}
