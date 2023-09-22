package com.github.eiriksgata.rulateday.platform.misc;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuthentication {
}
