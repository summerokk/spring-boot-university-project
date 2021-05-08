package com.att.university.validator.faculty.impl;

import com.att.university.request.faculty.FacultyUpdateRequest;
import com.att.university.validator.faculty.FacultyUpdateValidator;
import org.springframework.stereotype.Component;

@Component
public class FacultyUpdateValidatorImpl implements FacultyUpdateValidator {
    @Override
    public void validate(FacultyUpdateRequest updateRequest) {
        validateNull(updateRequest.getName(), "Faculty name is null");

        validateEmpty(updateRequest.getName(), "Faculty name is empty");

        validateNull(updateRequest.getId(), "Faculty id is null");
    }
}
