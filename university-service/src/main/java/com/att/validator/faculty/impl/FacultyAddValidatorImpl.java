package com.att.validator.faculty.impl;

import com.att.request.faculty.FacultyAddRequest;
import com.att.validator.faculty.FacultyAddValidator;
import org.springframework.stereotype.Component;

@Component
public class FacultyAddValidatorImpl implements FacultyAddValidator {
    @Override
    public void validate(FacultyAddRequest addRequest) {
        validateNull(addRequest.getName(), "Faculty name is null");

        validateEmpty(addRequest.getName(), "Faculty name is empty");
    }
}
