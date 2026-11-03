package edu.wne.cs366;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

class StudentKaratsubaTest {

    @Test
    void multipliesByZero() {
        assertEquals(BigInteger.ZERO,
                KaratsubaMultiplier.karatsuba(BigInteger.ZERO, new BigInteger("987654321")));
        assertEquals(BigInteger.ZERO,
                KaratsubaMultiplier.karatsuba(new BigInteger("-12345"), BigInteger.ZERO));
    }

    @Test
    void multipliesOneDigitValues() {
        assertEquals(BigInteger.valueOf(56),
                KaratsubaMultiplier.karatsuba(BigInteger.valueOf(7), BigInteger.valueOf(8)));
        assertEquals(BigInteger.valueOf(9),
                KaratsubaMultiplier.karatsuba(BigInteger.valueOf(3), BigInteger.valueOf(3)));
    }

    @Test
    void handlesUnequalDigitCounts() {
        assertMatchesJavaMultiply("123", "45678");
        assertMatchesJavaMultiply("987654321", "12");
    }

    @Test
    void handlesOneNegativeFactor() {
        assertEquals(new BigInteger("-56088"),
                KaratsubaMultiplier.karatsuba(new BigInteger("-123"), new BigInteger("456")));
        assertEquals(new BigInteger("-56088"),
                KaratsubaMultiplier.karatsuba(new BigInteger("123"), new BigInteger("-456")));
    }

    @Test
    void handlesTwoNegativeFactors() {
        assertEquals(new BigInteger("56088"),
                KaratsubaMultiplier.karatsuba(new BigInteger("-123"), new BigInteger("-456")));
    }

    @Test
    void handlesLargeValuesAcrossSeveralRecursiveLevels() {
        assertMatchesJavaMultiply(
                "1234567890123456789012345678901234567890",
                "9876543210987654321098765432109876543210");
    }

    @Test
    void matchesBigIntegerMultiplyForSeveralPairs() {
        assertMatchesJavaMultiply("0", "999999999999999999");
        assertMatchesJavaMultiply("7", "8");
        assertMatchesJavaMultiply("123456", "789012");
        assertMatchesJavaMultiply("-3141592653589793", "2718281828459045");
        assertMatchesJavaMultiply("-12345678901234567890", "-98765432109876543210");
    }

    @Test
    void rejectsNullArguments() {
        assertThrows(NullPointerException.class,
                () -> KaratsubaMultiplier.karatsuba(null, BigInteger.ONE));
        assertThrows(NullPointerException.class,
                () -> KaratsubaMultiplier.karatsuba(BigInteger.ONE, null));
    }

    private void assertMatchesJavaMultiply(String x, String y) {
        BigInteger first = new BigInteger(x);
        BigInteger second = new BigInteger(y);

        assertEquals(first.multiply(second),
                KaratsubaMultiplier.karatsuba(first, second));
    }
}