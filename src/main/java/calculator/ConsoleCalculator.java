package calculator;

import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleCalculator {

    private static final String RESET_COMMAND = "c";
    private static final String STOP_COMMAND = "s";

    private enum Step {
        FIRST_OPERAND,
        OPERATION,
        SECOND_OPERAND
    }

    private final Calculator calculator = new Calculator();
    private final Scanner scanner;
    private final PrintStream out;

    public ConsoleCalculator(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    public void run() {
        Step step = Step.FIRST_OPERAND;
        double result = 0;
        char operation = 0;

        while (true) {
            out.print(step == Step.OPERATION ? "Введите операцию: " : "Введите число: ");
            if (!scanner.hasNext()) {
                return;
            }
            String input = scanner.next();

            if (input.equalsIgnoreCase(STOP_COMMAND)) {
                out.println("Работа завершена");
                return;
            }
            if (input.equalsIgnoreCase(RESET_COMMAND)) {
                result = 0;
                step = Step.FIRST_OPERAND;
                out.println("Результат сброшен");
                continue;
            }

            switch (step) {
                case FIRST_OPERAND:
                    if (isNumber(input)) {
                        result = Double.parseDouble(input);
                        step = Step.OPERATION;
                    } else {
                        out.println("Ошибка: введите число");
                    }
                    break;
                case OPERATION:
                    if (input.length() == 1 && calculator.isSupported(input.charAt(0))) {
                        operation = input.charAt(0);
                        step = Step.SECOND_OPERAND;
                    } else {
                        out.println("Ошибка: неподдерживаемая операция");
                    }
                    break;
                case SECOND_OPERAND:
                    if (!isNumber(input)) {
                        out.println("Ошибка: введите число");
                        break;
                    }
                    try {
                        result = calculator.calculate(result, operation, Double.parseDouble(input));
                    } catch (ArithmeticException e) {
                        out.println("Ошибка: деление на ноль");
                    }
                    out.println("Результат: " + result);
                    step = Step.OPERATION;
                    break;
            }
        }
    }

    private boolean isNumber(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
