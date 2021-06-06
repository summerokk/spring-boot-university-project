package com.att.validator.person;

import com.att.request.person.student.StudentUpdateRequest;

public interface StudentUpdateValidator extends PersonValidator {
    void validate(StudentUpdateRequest updateRequest);
}
