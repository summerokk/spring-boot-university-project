package com.att.university.validator.person;

import com.att.university.request.person.student.StudentRegisterRequest;

public interface StudentRegisterValidator extends PersonValidator {
    void validate(StudentRegisterRequest registerRequest);
}
