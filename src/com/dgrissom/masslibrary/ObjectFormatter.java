package com.dgrissom.masslibrary;

import java.lang.reflect.Field;

public final class ObjectFormatter {
    private ObjectFormatter() {}

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
                String s;
                try {
                    String value = String.valueOf(field.get(object));
                    s = varName + "=" + value + ", ";
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    s = varName + "=" + "{Unavailable}, ";
                }

                string += s;
            }
        }
        // remove last ", "
        if (string.endsWith(", "))
            string = string.substring(0, string.length() - 2);
        string += "]";
        return string;
    }
}
