package com.github.fit.common;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, PARAMETER, LOCAL_VARIABLE, CONSTRUCTOR })
@Retention(RUNTIME)
@Documented
public @interface DynamicPort {
}
