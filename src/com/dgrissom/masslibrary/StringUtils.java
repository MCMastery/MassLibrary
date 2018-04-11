package com.dgrissom.masslibrary;

import java.text.DecimalFormat;

public final class StringUtils {
    private StringUtils() {}

    public static String repeat(String s, int times) {
        return repeat(s, "", times);
    }
    public static String repeat(String s, String separator, int times) {
        if (times < 0)
            throw new IllegalArgumentException("cannot multiply string by negative number");
        String result = "";
        for (int i = 0; i < times; i++)
            result += s + separator;
        // remove last delimiter
        if (result.endsWith(separator))
            result = result.substring(0, result.length() - separator.length());
        return result;
    }

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

    // outputs hex value of the given integer
    public static String hex(int i) {
        return String.format("0x%08X", i);
    }

    public static String fromDuration(long millis, boolean includeMs) {
        long second = (millis / 1000) % 60;
        long minute = (millis / (1000 * 60)) % 60;
        long hour = (millis / (1000 * 60 * 60)) % 24;

        if (includeMs)
            return String.format("%02dh:%02dm:%02ds:%dms", hour, minute, second, millis);
        return String.format("%02dh:%02dm:%02ds", hour, minute, second);
    }

    public static String fromDouble(double d, int decimalPlaces) {
        String decimalPlacesString = repeat("0", decimalPlaces);
        DecimalFormat df = new DecimalFormat("0." + decimalPlacesString);
        return df.format(d);
    }
}
