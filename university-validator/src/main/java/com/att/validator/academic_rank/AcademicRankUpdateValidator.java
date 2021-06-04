package com.att.validator.academic_rank;

import com.att.request.academic_rank.AcademicRankUpdateRequest;
import com.att.validator.Validator;

public interface AcademicRankUpdateValidator extends Validator {
    void validate(AcademicRankUpdateRequest updateRequest);
}
