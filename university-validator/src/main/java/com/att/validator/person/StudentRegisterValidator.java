package com.att.validator.person;

import com.att.request.person.student.StudentRegisterRequest;

public interface StudentRegisterValidator extends PersonValidator {
    void validate(StudentRegisterRequest registerRequest);
}
