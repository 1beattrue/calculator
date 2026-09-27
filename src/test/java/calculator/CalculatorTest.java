package calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    void addsNumbers() {
        assertEquals(5.5, calculator.calculate(2.5, '+', 3));
    }

    @Test
    void subtractsNumbers() {
        assertEquals(-1.0, calculator.calculate(2, '-', 3));
    }

    @Test
    void multipliesNumbers() {
        assertEquals(7.5, calculator.calculate(2.5, '*', 3));
    }

    @Test
    void dividesNumbers() {
        assertEquals(2.5, calculator.calculate(5, '/', 2));
    }

    @Test
    void throwsOnDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> calculator.calculate(5, '/', 0));
    }

    @Test
    void throwsOnUnsupportedOperation() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(5, '%', 2));
    }

    @Test
    void recognizesSupportedOperations() {
        assertTrue(calculator.isSupported('+'));
        assertTrue(calculator.isSupported('-'));
        assertTrue(calculator.isSupported('*'));
        assertTrue(calculator.isSupported('/'));
    }

    @Test
    void rejectsUnsupportedOperations() {
        assertFalse(calculator.isSupported('%'));
        assertFalse(calculator.isSupported('^'));
        assertFalse(calculator.isSupported('a'));
    }
}
