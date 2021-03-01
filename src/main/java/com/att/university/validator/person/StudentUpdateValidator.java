package com.att.university.validator.person;

import com.att.university.request.person.student.StudentUpdateRequest;

public interface StudentUpdateValidator extends PersonValidator {
    void validate(StudentUpdateRequest updateRequest);
}
