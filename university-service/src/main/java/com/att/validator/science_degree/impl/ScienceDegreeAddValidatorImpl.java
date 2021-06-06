package com.att.validator.science_degree.impl;

import com.att.request.science_degree.ScienceDegreeAddRequest;
import com.att.validator.science_degree.ScienceDegreeAddValidator;
import org.springframework.stereotype.Component;

@Component
public class ScienceDegreeAddValidatorImpl implements ScienceDegreeAddValidator {
    @Override
    public void validate(ScienceDegreeAddRequest addRequest) {
        validateNull(addRequest.getName(), "Science Degree name is null");

        validateEmpty(addRequest.getName(), "Science Degree name is empty");
    }
}
