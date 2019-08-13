package com.pss.oneservice.common.integration.config;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Documented
@Retention(RUNTIME)
@Constraint(validatedBy = CustomValidator.class)
public @interface SetValidation {

    String PC_REQ() default "Y";
    String SEC_REQ() default "Y";
    String JSON_VAL_REQ() default "Y";
}
