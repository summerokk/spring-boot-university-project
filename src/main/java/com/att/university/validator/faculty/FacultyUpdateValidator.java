package com.att.university.validator.faculty;

import com.att.university.request.faculty.FacultyUpdateRequest;
import com.att.university.validator.Validator;

public interface FacultyUpdateValidator extends Validator {
    void validate(FacultyUpdateRequest updateRequest);
}
