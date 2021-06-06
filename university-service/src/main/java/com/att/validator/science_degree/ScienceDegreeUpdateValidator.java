package com.att.validator.science_degree;

import com.att.request.science_degree.ScienceDegreeUpdateRequest;
import com.att.validator.Validator;

public interface ScienceDegreeUpdateValidator extends Validator {
    void validate(ScienceDegreeUpdateRequest updateRequest);
}
