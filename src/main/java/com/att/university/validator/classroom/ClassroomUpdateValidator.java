package com.att.university.validator.classroom;

import com.att.university.request.classroom.ClassroomUpdateRequest;
import com.att.university.validator.Validator;

public interface ClassroomUpdateValidator extends Validator {
    void validate(ClassroomUpdateRequest updateRequest);
}
