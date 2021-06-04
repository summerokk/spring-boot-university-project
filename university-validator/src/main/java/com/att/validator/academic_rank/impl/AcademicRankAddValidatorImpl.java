package com.att.validator.academic_rank.impl;

import com.att.request.academic_rank.AcademicRankAddRequest;
import com.att.validator.academic_rank.AcademicRankAddValidator;
import org.springframework.stereotype.Component;

@Component
public class AcademicRankAddValidatorImpl implements AcademicRankAddValidator {
    @Override
    public void validate(AcademicRankAddRequest addRequest) {
        validateNull(addRequest.getName(), "Academic rank name is null");

        validateEmpty(addRequest.getName(), "Academic rank name is empty");
    }
}
