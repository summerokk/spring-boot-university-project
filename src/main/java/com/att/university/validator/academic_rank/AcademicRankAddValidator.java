package com.att.university.validator.academic_rank;

import com.att.university.request.academic_rank.AcademicRankAddRequest;
import com.att.university.validator.Validator;

public interface AcademicRankAddValidator extends Validator {
    void validate(AcademicRankAddRequest addRequest);
}
