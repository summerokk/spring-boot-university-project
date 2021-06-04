package com.att.validator.academic_rank;

import com.att.request.academic_rank.AcademicRankAddRequest;
import com.att.validator.Validator;

public interface AcademicRankAddValidator extends Validator {
    void validate(AcademicRankAddRequest addRequest);
}
