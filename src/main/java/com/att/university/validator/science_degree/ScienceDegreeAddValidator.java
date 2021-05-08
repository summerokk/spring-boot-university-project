package com.att.university.validator.science_degree;

import com.att.university.request.science_degree.ScienceDegreeAddRequest;
import com.att.university.validator.Validator;

public interface ScienceDegreeAddValidator extends Validator {
    void validate(ScienceDegreeAddRequest addRequest);
}
