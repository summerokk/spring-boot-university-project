package com.att.university.validator.science_degree;

import com.att.university.request.science_degree.ScienceDegreeUpdateRequest;
import com.att.university.validator.Validator;

public interface ScienceDegreeUpdateValidator extends Validator {
    void validate(ScienceDegreeUpdateRequest updateRequest);
}
