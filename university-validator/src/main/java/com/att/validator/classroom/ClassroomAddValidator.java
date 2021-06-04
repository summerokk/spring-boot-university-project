package com.att.validator.classroom;

import com.att.request.classroom.ClassroomAddRequest;
import com.att.validator.Validator;

public interface ClassroomAddValidator extends Validator {
    void validate(ClassroomAddRequest addRequest);
}
