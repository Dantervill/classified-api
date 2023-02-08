package com.senla.internship.classifiedapi.bean.validation.constraints;

import com.senla.internship.classifiedapi.bean.validation.validators.ValueOfEnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be one of the constants of specified enum class.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ValueOfEnumValidator.class)
public @interface ValueOfEnum {
    Class<? extends Enum<?>> enumClass();
    String message() default "Must be any of enum {enumClass}.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
