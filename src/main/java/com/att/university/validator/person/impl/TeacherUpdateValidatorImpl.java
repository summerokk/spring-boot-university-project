package com.att.university.validator.person.impl;

import com.att.university.request.person.teacher.TeacherUpdateRequest;
import com.att.university.validator.person.TeacherUpdateValidator;
import org.springframework.beans.factory.annotation.Value;
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
