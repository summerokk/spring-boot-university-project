package com.att.university.validator.faculty.impl;

import com.att.university.request.faculty.FacultyAddRequest;
import com.att.university.validator.faculty.FacultyAddValidator;
import org.springframework.stereotype.Component;

@Component
public class FacultyAddValidatorImpl implements FacultyAddValidator {
    @Override
    public void validate(FacultyAddRequest addRequest) {
        validateNull(addRequest.getName(), "Faculty name is null");

        validateEmpty(addRequest.getName(), "Faculty name is empty");
    }
}
