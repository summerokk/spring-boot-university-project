package com.att.university.validator.faculty;

import com.att.university.request.faculty.FacultyAddRequest;
import com.att.university.validator.Validator;

public interface FacultyAddValidator extends Validator {
    void validate(FacultyAddRequest addRequest);
}
