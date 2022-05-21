package com.webapplication.watchlist.Annotation;

import com.webapplication.watchlist.Annotation.Priority;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PriorityValidator implements ConstraintValidator<Priority,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.trim().length() == 1 && "LMH".contains(value.toUpperCase());
    }
}
