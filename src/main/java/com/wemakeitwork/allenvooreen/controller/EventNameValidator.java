package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.service.EventNameConstraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EventNameValidator implements ConstraintValidator<EventNameConstraint, String> {
    @Override
    public void initialize(EventNameConstraint contactNumber) {
    }

    @Override
    public boolean isValid(String eventNameField, ConstraintValidatorContext cxt) {
        return !eventNameField.isEmpty() && !eventNameField.isBlank();
    }
}
