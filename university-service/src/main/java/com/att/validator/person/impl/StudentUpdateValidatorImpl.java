package com.att.validator.person.impl;

import com.att.request.person.student.StudentUpdateRequest;
import com.att.validator.person.StudentUpdateValidator;
import org.springframework.stereotype.Component;

@Component
public class StudentUpdateValidatorImpl extends AbstractPersonValidatorImpl<StudentUpdateRequest>
        implements StudentUpdateValidator {
    @Override
    public void validate(StudentUpdateRequest updateRequest) {
        baseInfoValidate(updateRequest);

        validateNull(updateRequest.getId(), "Student ID is null");
    }
}
