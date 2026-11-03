package edu.wne.cs366;

import java.math.BigInteger;

/**
 * CS366 Programming Assignment 02: Karatsuba Multiplication.
 *
 * @author Lavakumar
 */
public class KaratsubaMultiplier {

    private static final BigInteger TEN = BigInteger.TEN;

    /**
     * Multiplies two signed integers using recursive Karatsuba multiplication.
     *
     * @param x first factor
     * @param y second factor
     * @return x multiplied by y
     * @throws NullPointerException when either argument is null
     */
    public static BigInteger karatsuba(BigInteger x, BigInteger y) {
        if (x == null || y == null) {
            throw new NullPointerException("Arguments must not be null");
        }

        boolean negativeResult = x.signum() != y.signum();
        BigInteger product = karatsubaNonnegative(x.abs(), y.abs());

        return negativeResult ? product.negate() : product;
    }

    private static BigInteger karatsubaNonnegative(BigInteger x, BigInteger y) {
        // Multiplication by zero is an immediate base case.
        if (x.signum() == 0 || y.signum() == 0) {
            return BigInteger.ZERO;
        }

        int n = Math.max(decimalDigits(x), decimalDigits(y));

        // Ordinary multiplication is used only in the base case.
        if (n == 1) {
            return x.multiply(y);
        }

        int m = n / 2;
        BigInteger powerOfTen = TEN.pow(m);

        BigInteger[] xParts = x.divideAndRemainder(powerOfTen);
        BigInteger[] yParts = y.divideAndRemainder(powerOfTen);

        BigInteger xHigh = xParts[0];
        BigInteger xLow = xParts[1];
        BigInteger yHigh = yParts[0];
        BigInteger yLow = yParts[1];

        BigInteger highProduct = karatsubaNonnegative(xHigh, yHigh);
        BigInteger lowProduct = karatsubaNonnegative(xLow, yLow);
        BigInteger sumProduct = karatsubaNonnegative(
                xHigh.add(xLow),
                yHigh.add(yLow));

        BigInteger crossProduct = sumProduct.subtract(highProduct).subtract(lowProduct);

        return highProduct.multiply(TEN.pow(2 * m))
                .add(crossProduct.multiply(powerOfTen))
                .add(lowProduct);
    }

    private static int decimalDigits(BigInteger value) {
        return value.toString().length();
    }

    public static void main(String[] args) {
        BigInteger x = new BigInteger("3412");
        BigInteger y = new BigInteger("1523");
        System.out.printf("%s × %s = %s%n", x, y, karatsuba(x, y));
    }
}