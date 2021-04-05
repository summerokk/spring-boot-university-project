package com.att.university.validator.person.impl;

import com.att.university.request.person.student.StudentRegisterRequest;
import com.att.university.validator.person.StudentRegisterValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StudentRegisterValidatorImpl extends AbstractPersonValidatorImpl<StudentRegisterRequest>
        implements StudentRegisterValidator {
    public StudentRegisterValidatorImpl(@Value("${password.min.length}") int minPasswordLength) {
        super(minPasswordLength);
    }

    @Override
    public void validate(StudentRegisterRequest registerRequest) {
        baseInfoValidate(registerRequest);
    }
}
