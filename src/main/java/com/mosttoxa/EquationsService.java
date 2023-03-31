package com.mosttoxa;

import org.mariuszgromada.math.mxparser.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class EquationsService {
    private static final Logger LOG = Logger.getLogger(EquationsService.class.getSimpleName());

    @Autowired
    EquationsRepository repo;

    // Method to add equation to db if it's correct
    public void persist(String equation, String result) {
        if (!result.equals("NaN")) {
            Equation e = new Equation();
            e.setEquation(equation);
            e.setResult(result);
            repo.insert(e);
        } else {
            LOG.warning("Equation syntax is not valid");
        }
    }

    // Method to solve user's input equation and add equation to db
    public void solve(String input) {
        Equation e = repo.findByEquation(input);
        String result;
        if (e != null) {
            LOG.info("---> " + e.getResult() + " ...  done.");
        } else {
            result = calculateEquation(input);
            persist(input, result);
        }
    }

    // Method to check if user's input root of equation is correct solvation
    // and add successful case to db if it not exists there
    public void check(String input, String root) {
        try {
            double rootToCheck = Double.valueOf(root);
            String result = calculateEquation(input);
            if (rootToCheck == Double.valueOf(result)) {
                Equation e = repo.findByEquation(input);
                if (e == null) {
                    persist(input, root);
                    LOG.info("Equation's root is correct");
                } else {
                    LOG.info("Equation's root is correct");
                }
            } else {
                LOG.warning("Equation's root is not correct");
            }
        } catch (NumberFormatException e) {
            LOG.warning("Equation's root is not a number");
        }
    }

    // Method to find all equations from db with given root
    public void findByRoot(String root) {
        List<Equation> le = null;
        try {
            le = repo.findByResult(Double.valueOf(root));
        } catch (NumberFormatException e) {
            LOG.warning("Equation's root is not a number");
        }
        for (Equation e : le) {
            LOG.info(e.toString());
        }
    }

    // Method to calculate the given equation
    private String calculateEquation(String equation) {
        //bring the equation to the form f(x) = 0 to use mXparser calculate method
        if (!equation.contains("=")) {
            LOG.warning("Invalid equation. Correct equation must contain =");
            return "NaN";
        }
        //bring the equation to the form f(x) = 0 to use mXparser calculate method
        String[] sides = equation.split("=");
        String eq = sides[0] + "-(" + sides[1] + ")";
        License.iConfirmNonCommercialUse("mosttoxa");
        Expression e = new Expression("solve(" + eq + ", x," + Integer.MIN_VALUE + "," + Integer.MAX_VALUE + ")");
        e.setVerboseMode();
        return String.valueOf(e.calculate());
    }
}