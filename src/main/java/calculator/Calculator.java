package calculator;

public class Calculator {

    public boolean isSupported(char operation) {
        return switch (operation) {
            case '+', '-', '*', '/' -> true;
            default -> false;
        };
    }

    public double calculate(double firstOperand, char operation, double secondOperand) {
        return switch (operation) {
            case '+' -> add(firstOperand, secondOperand);
            case '-' -> subtract(firstOperand, secondOperand);
            case '*' -> multiply(firstOperand, secondOperand);
            case '/' -> divide(firstOperand, secondOperand);
            default -> throw new IllegalArgumentException("Неподдерживаемая операция: " + operation);
        };
    }

    private double add(double firstOperand, double secondOperand) {
        return firstOperand + secondOperand;
    }

    private double subtract(double firstOperand, double secondOperand) {
        return firstOperand - secondOperand;
    }

    private double multiply(double firstOperand, double secondOperand) {
        return firstOperand * secondOperand;
    }

    private double divide(double firstOperand, double secondOperand) {
        if (secondOperand == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        return firstOperand / secondOperand;
    }
}
