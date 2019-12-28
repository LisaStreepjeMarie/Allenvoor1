package com.wemakeitwork.allenvooreen.service;

import com.wemakeitwork.allenvooreen.controller.EventNameValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EventNameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EventNameConstraint {
    String message() default "Ongeldige Afspraaknaam";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}