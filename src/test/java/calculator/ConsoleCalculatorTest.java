package calculator;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleCalculatorTest {

    private String run(String input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(output, true, StandardCharsets.UTF_8);
        new ConsoleCalculator(new Scanner(input), out).run();
        return output.toString(StandardCharsets.UTF_8);
    }

    private int count(String text, String part) {
        return text.split(Pattern.quote(part), -1).length - 1;
    }

    @Test
    void readsFirstNumberOperationSecondNumberAndPrintsResult() {
        String output = run("2 + 3 s");

        assertTrue(output.contains("Результат: 5.0"));
    }

    @Test
    void readsFractionalNumbers() {
        String output = run("2.5 * 2 s");

        assertTrue(output.contains("Результат: 5.0"));
    }

    @Test
    void usesResultAsFirstOperandOfNextOperation() {
        String output = run("2 + 3 * 4 - 5 / 3 s");

        assertTrue(output.contains("Результат: 20.0"));
        assertTrue(output.contains("Результат: 15.0"));
        assertEquals(2, count(output, "Результат: 5.0"));
    }

    @Test
    void reportsDivisionByZeroAndKeepsResult() {
        String output = run("5 / 0 + 1 s");

        assertTrue(output.contains("Ошибка: деление на ноль"));
        assertTrue(output.contains("Результат: 5.0"));
        assertTrue(output.contains("Результат: 6.0"));
    }

    @Test
    void reportsUnsupportedOperationAndAsksAgain() {
        String output = run("5 % + 1 s");

        assertTrue(output.contains("Ошибка: неподдерживаемая операция"));
        assertTrue(output.contains("Результат: 6.0"));
    }

    @Test
    void reportsInvalidNumberAndAsksAgain() {
        String output = run("abc 2 + xyz 3 s");

        assertEquals(2, count(output, "Ошибка: введите число"));
        assertTrue(output.contains("Результат: 5.0"));
    }

    @Test
    void resetsResultAndStartsFromFirstNumber() {
        String output = run("2 + 3 C 10 - 4 s");

        assertTrue(output.contains("Результат сброшен"));
        assertTrue(output.contains("Результат: 6.0"));
        assertFalse(output.contains("Результат: 1.0"));
    }

    @Test
    void handlesResetCommandInLowerCase() {
        String output = run("2 + 3 c 1 + 1 s");

        assertTrue(output.contains("Результат сброшен"));
        assertTrue(output.contains("Результат: 2.0"));
    }

    @Test
    void handlesResetCommandInsteadOfNumber() {
        String output = run("2 + c 7 * 2 s");

        assertTrue(output.contains("Результат сброшен"));
        assertTrue(output.contains("Результат: 14.0"));
    }

    @Test
    void stopsOnUpperCaseCommand() {
        String output = run("S 2 + 3");

        assertTrue(output.contains("Работа завершена"));
        assertFalse(output.contains("Результат"));
    }

    @Test
    void stopsOnLowerCaseCommandAfterCalculation() {
        String output = run("2 + 3 s 1 + 1");

        assertTrue(output.endsWith("Работа завершена" + System.lineSeparator()));
        assertFalse(output.contains("Результат: 6.0"));
    }

    @Test
    void finishesWhenInputEnds() {
        String output = run("2 + 3");

        assertTrue(output.contains("Результат: 5.0"));
        assertFalse(output.contains("Работа завершена"));
    }
}
