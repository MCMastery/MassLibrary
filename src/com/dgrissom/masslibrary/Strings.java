package com.dgrissom.masslibrary;

public final class Strings {
    private Strings() {}

    public static boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }
    public static boolean isDouble(String s) {
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            // first character could be '-' (negative)
            if (!isDigit(ch) && ch != '.' && !(i == 0 && ch == '-' && s.length() > 1))
                return false;
        }
        return true;
    }
}
