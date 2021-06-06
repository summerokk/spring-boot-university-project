package com.att.validator.faculty;

import com.att.request.faculty.FacultyAddRequest;
import com.att.validator.Validator;

public interface FacultyAddValidator extends Validator {
    void validate(FacultyAddRequest addRequest);
}
