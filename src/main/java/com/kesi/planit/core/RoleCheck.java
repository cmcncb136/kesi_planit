package com.kesi.planit.core;

import com.kesi.planit.core.role.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleCheck {
    Role allowLevel() default Role.USER;
}
