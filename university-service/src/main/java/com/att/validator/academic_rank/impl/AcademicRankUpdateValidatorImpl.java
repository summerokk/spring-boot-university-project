package com.att.validator.academic_rank.impl;

import com.att.request.academic_rank.AcademicRankUpdateRequest;
import com.att.validator.academic_rank.AcademicRankUpdateValidator;
import org.springframework.stereotype.Component;

@Component
public class AcademicRankUpdateValidatorImpl implements AcademicRankUpdateValidator {
    @Override
    public void validate(AcademicRankUpdateRequest updateRequest) {
        validateNull(updateRequest.getName(), "Academic rank name is null");

        validateEmpty(updateRequest.getName(), "Academic rank name is empty");

        validateNull(updateRequest.getId(), "Academic rank id is null");
    }
}
