package com.dgrissom.masslibrary;

public final class StringUtils {
    private StringUtils() {}

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

    public static String fromDuration(long millis) {
        long second = (millis / 1000) % 60;
        long minute = (millis / (1000 * 60)) % 60;
        long hour = (millis / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d:%d", hour, minute, second, millis);
    }
}
