package com.example.vehicleinspection.model.validators;

import com.example.vehicleinspection.dto.request.UserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EndDateAfterStartDateValidator implements ConstraintValidator<EndDateAfterStartDate, UserRequest> {
    @Override
    public boolean isValid(UserRequest request, ConstraintValidatorContext constraintValidatorContext) {
        if (request.getStartDate() == null || request.getEndDate() == null) {
            return true;
        }
        return request.getEndDate().isAfter(request.getStartDate());
    }
}
