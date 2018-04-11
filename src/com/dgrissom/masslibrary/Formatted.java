package com.dgrissom.masslibrary;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// Allows a variable to be displayed when using ObjectFormatter.format
@Retention(RetentionPolicy.RUNTIME)
public @interface Formatted {

}
