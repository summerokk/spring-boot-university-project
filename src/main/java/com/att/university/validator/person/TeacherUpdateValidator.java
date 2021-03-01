package com.att.university.validator.person;

import com.att.university.request.person.teacher.TeacherUpdateRequest;

public interface TeacherUpdateValidator extends PersonValidator {
    void validate(TeacherUpdateRequest registerRequest);
}
