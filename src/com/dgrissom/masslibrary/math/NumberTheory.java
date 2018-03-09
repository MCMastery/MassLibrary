package com.dgrissom.masslibrary.math;

public final class NumberTheory {
    private NumberTheory() {}

    public static boolean isEven(int a) {
        return a % 2 == 0;
    }
    public static boolean isOdd(int a) {
        return !isEven(a);
    }

    // no number divides a and b evenly except 1
    // so we only need to check divisors up to sqrt(min(a, b))
    public static boolean coprime(int a, int b) {
        for (double i = 2; i < Math.sqrt(Math.min(a, b)); i++)
            if (a / i == b / i)
                return false;
        return true;
    }
}
