package com.att.validator.classroom;

import com.att.request.classroom.ClassroomUpdateRequest;
import com.att.validator.Validator;

public interface ClassroomUpdateValidator extends Validator {
    void validate(ClassroomUpdateRequest updateRequest);
}
