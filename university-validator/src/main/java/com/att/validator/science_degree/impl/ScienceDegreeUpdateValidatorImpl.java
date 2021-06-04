package com.att.validator.science_degree.impl;

import com.att.request.science_degree.ScienceDegreeUpdateRequest;
import com.att.validator.science_degree.ScienceDegreeUpdateValidator;
import org.springframework.stereotype.Component;

@Component
public class ScienceDegreeUpdateValidatorImpl implements ScienceDegreeUpdateValidator {
    @Override
    public void validate(ScienceDegreeUpdateRequest updateRequest) {
        validateNull(updateRequest.getName(), "Science Degree name is null");

        validateEmpty(updateRequest.getName(), "Science Degree name is empty");

        validateNull(updateRequest.getId(), "Science Degree id is null");
    }
}
