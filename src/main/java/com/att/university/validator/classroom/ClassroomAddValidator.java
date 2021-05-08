package com.att.university.validator.classroom;

import com.att.university.request.classroom.ClassroomAddRequest;
import com.att.university.validator.Validator;

public interface ClassroomAddValidator extends Validator {
    void validate(ClassroomAddRequest addRequest);
}
