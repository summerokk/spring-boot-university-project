package com.att.validator.person.impl;

import com.att.request.person.teacher.TeacherUpdateRequest;
import com.att.validator.person.TeacherUpdateValidator;
import org.springframework.stereotype.Component;

@Component
public class TeacherUpdateValidatorImpl extends AbstractPersonValidatorImpl<TeacherUpdateRequest>
        implements TeacherUpdateValidator {
    @Override
    public void validate(TeacherUpdateRequest updateRequest) {
        baseInfoValidate(updateRequest);

        validateNull(updateRequest.getId(), "Teacher ID is null");
        validateNull(updateRequest.getLinkedin(), "Linkedin is null");
        validateNull(updateRequest.getAcademicRankId(), "Academic Rank Id is null");
        validateNull(updateRequest.getScienceDegreeId(), "Science Degree Id is null");
    }
}
