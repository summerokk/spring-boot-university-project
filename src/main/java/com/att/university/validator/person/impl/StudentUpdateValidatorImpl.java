package com.att.university.validator.person.impl;

import com.att.university.request.person.student.StudentUpdateRequest;
import com.att.university.validator.person.StudentUpdateValidator;
import org.springframework.stereotype.Component;

@Component
public class StudentUpdateValidatorImpl extends AbstractPersonValidatorImpl<StudentUpdateRequest>
        implements StudentUpdateValidator {
    @Override
    public void validate(StudentUpdateRequest updateRequest) {
        baseInfoValidate(updateRequest);

        if (updateRequest.getId() == null) {
            throw new RuntimeException("Student ID is null");
        }
    }
}
