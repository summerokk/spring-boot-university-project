package com.att.university.validator.academic_rank;

import com.att.university.request.academic_rank.AcademicRankUpdateRequest;
import com.att.university.validator.Validator;

public interface AcademicRankUpdateValidator extends Validator {
    void validate(AcademicRankUpdateRequest updateRequest);
}
