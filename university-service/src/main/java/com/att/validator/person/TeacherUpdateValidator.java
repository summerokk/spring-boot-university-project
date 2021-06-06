package com.att.validator.person;

import com.att.request.person.teacher.TeacherUpdateRequest;

public interface TeacherUpdateValidator extends PersonValidator {
    void validate(TeacherUpdateRequest registerRequest);
}
