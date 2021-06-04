package com.att.validator.person.impl;

import com.att.request.person.student.StudentRegisterRequest;
import com.att.validator.person.StudentRegisterValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StudentRegisterValidatorImpl extends AbstractPersonValidatorImpl<StudentRegisterRequest>
        implements StudentRegisterValidator {
    private final int minPasswordLength;

    public StudentRegisterValidatorImpl(@Value("${password.min.length}") int minPasswordLength) {
        this.minPasswordLength = minPasswordLength;
    }

    @Override
    public void validate(StudentRegisterRequest registerRequest) {
        validateNull(registerRequest.getPassword(), "Password is null");

        validatePasswordLength(registerRequest.getPassword(), minPasswordLength);
        validatePasswordEquality(registerRequest.getPassword(), registerRequest.getPasswordConfirm());

        baseInfoValidate(registerRequest);
    }
}
