package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class EquationsService {
    private static final Logger LOG = Logger.getLogger(EquationsService.class.getSimpleName());
    @Autowired
    EquationsRepository repo;

    public void service(String input) {
        if (isEquationValid(input)) {
            Equation e = new Equation();
            e.setEquation(input);
            repo.insert(e);
            LOG.info(solveEquation(input));
        }
    }
    private boolean isEquationValid(String equation) {
        // Check if the equation contains any invalid characters
        if (!equation.matches("[0-9x.+\\-*/()=s]+")) {
            LOG.warning("Invalid equation. Please use only digits, x, +, -, *, /, (, ), and =.");
            return false;
        }

        // Check if the equation contains two consecutive operators
        if (equation.matches(".*[+\\-*/.]{2,}.*")) {
            LOG.warning("Invalid equation. Please ensure that no two consecutive operators are used.");
            return false;
        }

        // Check if the equation contains any division by zero
        if (equation.matches(".*/0.*")) {
            LOG.warning("Invalid equation. Division by zero is not allowed.");
            return false;
        }

        // Check if the equation contains only two parts splited by '='
        String[] sides = equation.split("=");
        if (sides.length != 2) {
            LOG.warning("Invalid equation. Please ensure that there is exactly one equals sign.");
            return false;
        }
        return true;
    }
    // Method to solve the given equation
    private String solveEquation(String equation) {
        // Split the equation at the = sign
        String[] sides = equation.split("=");

        // Initialize the coefficients and constants for each side of the equation
        double leftCoeff = 0;
        double leftConst = 0;
        double rightCoeff = 0;
        double rightConst = 0;

        // Process each term on the left side of the equation
        for (String term : sides[0].split("(?=[+-])")) {
            if (term.contains("*")) {
                // Handle multiplication
                String[] parts = term.split("\\*");
                double coeff = Double.parseDouble(parts[0]);
                if (parts[1].contains("x")) {
                    leftCoeff += coeff;
                } else {
                    leftConst += coeff * Double.parseDouble(parts[1]);
                }
            } else if (term.contains("/")) {
                // Handle division
                String[] parts = term.split("/");
                double coeff = Double.parseDouble(parts[0]);
                if (parts[1].contains("x")) {
                    leftCoeff += 1 / coeff;
                } else {
                    leftConst += Double.parseDouble(parts[1]) / coeff;
                }
            } else if (term.contains("x")) {
                // Handle x term
                String[] parts = term.split("x");
                if (parts[0].isEmpty() || parts[0].equals("+")) {
                    leftCoeff += 1;
                } else if (parts[0].equals("-")) {
                    leftCoeff -= 1;
                } else {
                    leftCoeff += Double.parseDouble(parts[0]);
                }
            } else {
                // Handle constant term
                leftConst += Double.parseDouble(term);
            }
        }

        // Process each term on the right side of the equation
        for (String term : sides[1].split("(?=[+-])")) {
            if (term.contains("*")) {
                // Handle multiplication
                String[] parts = term.split("\\*");
                double coeff = Double.parseDouble(parts[0]);
                if (parts[1].contains("x")) {
                    rightCoeff += coeff;
                } else {
                    rightConst += coeff * Double.parseDouble(parts[1]);
                }
            } else if (term.contains("/")) {
                // Handle division
                String[] parts = term.split("/");
                double coeff = Double.parseDouble(parts[0]);
                if (parts[1].contains("x")) {
                    rightCoeff += 1 / coeff;
                } else {
                    rightConst += Double.parseDouble(parts[1]) / coeff;
                }
            } else if (term.contains("x")) {
                // Handle x term
                String[] parts = term.split("x");
                if (parts[0].isEmpty() || parts[0].equals("+")) {
                    rightCoeff += 1;
                } else if (parts[0].equals("-")) {
                    rightCoeff -= 1;
                } else {
                    rightCoeff += Double.parseDouble(parts[0]);
                }
            } else {
                // Handle constant term
                rightConst += Double.parseDouble(term);
            }
        }

        // Combine the left and right coefficients and constants
        double coeff = leftCoeff - rightCoeff;
        double constant = rightConst - leftConst;

        // Solve for x and return the result
        if (coeff == 0) {
            if (constant == 0) {
                return "Infinite solutions";
            } else {
                return "No solution";
            }
        } else {
            double x = constant / coeff;
            return "x = " + x;
        }
    }
}
