package org.example;

import org.mariuszgromada.math.mxparser.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class EquationsService {
    private static final Logger LOG = Logger.getLogger(EquationsService.class.getSimpleName());
    @Autowired
    EquationsRepository repo;

    public void solve(String input) {
        String result = solveEquation(input);
        if (!result.equals("NaN")) {
            Equation e = new Equation();
            e.setEquation(input);
            e.setResult(result);
            repo.insert(e);
        } else {
            LOG.warning("Equation syntax is not valid");
        }
    }

    public void check(String input, String root) {
        try {
            double rootToCheck = Double.valueOf(root);
            String result = solveEquation(input);
            if (!result.equals("NaN")) {
                if (rootToCheck == Double.valueOf(result)) {
                    Equation e = new Equation();
                    e.setEquation(input);
                    e.setResult(result);
                    repo.insert(e);
                } else {
                    LOG.warning("Equation's root is not correct");
                }
            } else {
                LOG.warning("Equation syntax is not valid");
            }

        } catch (NumberFormatException e) {
            LOG.warning("Equation's root is not a number");
        }
    }

    // Method to solve the given equation
    private String solveEquation(String equation) {
        //bring the equation to the form f(x) = 0 to use mXparser calculate method
        if (!equation.contains("=")) {
            LOG.warning("Invalid equation. Correct equation must contain =");
            return "NaN";
        }
        String[] sides = equation.split("=");
        String eq = sides[0] + "-(" + sides[1] + ")";
        License.iConfirmNonCommercialUse("mosttoxa");
        Expression e = new Expression("solve(" + eq + ", x," + Integer.MIN_VALUE + "," + Integer.MAX_VALUE + ")");
        e.setVerboseMode();
        return String.valueOf(e.calculate());
    }
}