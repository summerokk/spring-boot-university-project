package com.att.university.validator.person;

import com.att.university.request.person.teacher.TeacherRegisterRequest;

public interface TeacherRegisterValidator extends PersonValidator {
    void validate(TeacherRegisterRequest registerRequest);
}
