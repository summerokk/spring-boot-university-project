package com.att.validator.science_degree;

import com.att.request.science_degree.ScienceDegreeAddRequest;
import com.att.validator.Validator;

public interface ScienceDegreeAddValidator extends Validator {
    void validate(ScienceDegreeAddRequest addRequest);
}
