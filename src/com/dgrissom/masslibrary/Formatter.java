package com.dgrissom.masslibrary;

import java.lang.reflect.Field;

public final class Formatter {
    private Formatter() {}

    // Returns a string containing the object's information, for debugging
    // Format: ClassName[var1=somevalue, var2=somevalue]
    // The object's variables must have the Formatted annotation to be displayed here
    public static String format(Object object) {
        String className = object.getClass().getSimpleName();
        Field[] fields = object.getClass().getDeclaredFields();
        String string = className + "[";
        for (Field field : fields) {
            field.setAccessible(true);
            Formatted annotation = field.getAnnotation(Formatted.class);
            if (annotation != null) {
                String varName = field.getName();
                try {
                    String value = String.valueOf(field.get(object));
                    string += varName + "=" + value + ", ";
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    string += varName + "=" + "{Unavailable}, ";
                }
            }
        }
        // remove last ", "
        if (string.endsWith(", "))
            string = string.substring(0, string.length() - 2);
        string += "]";
        return string;
    }
}
