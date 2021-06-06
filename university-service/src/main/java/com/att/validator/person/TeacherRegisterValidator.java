package com.att.validator.person;

import com.att.request.person.teacher.TeacherRegisterRequest;

public interface TeacherRegisterValidator extends PersonValidator {
    void validate(TeacherRegisterRequest registerRequest);
}
