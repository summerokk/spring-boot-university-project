package com.att.validator.faculty;

import com.att.request.faculty.FacultyUpdateRequest;
import com.att.validator.Validator;

public interface FacultyUpdateValidator extends Validator {
    void validate(FacultyUpdateRequest updateRequest);
}
