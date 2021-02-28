package com.att.university.validator.person.impl;

import com.att.university.request.person.teacher.TeacherUpdateRequest;
import com.att.university.validator.person.TeacherUpdateValidator;
import org.springframework.stereotype.Component;

@Component
public class TeacherUpdateValidatorImpl extends AbstractPersonValidatorImpl<TeacherUpdateRequest>
        implements TeacherUpdateValidator {
    @Override
    public void validate(TeacherUpdateRequest updateRequest) {
        baseInfoValidate(updateRequest);

        if (updateRequest.getId() == null) {
            throw new RuntimeException("Teacher ID is null");
        }

        if (updateRequest.getLinkedin() == null) {
            throw new RuntimeException("Linkedin is null");
        }

        if (updateRequest.getAcademicRankId() == null) {
            throw new RuntimeException("Academic Rank Id is null");
        }

        if (updateRequest.getScienceDegreeId() == null) {
            throw new RuntimeException("Science Degree Id is null");
        }
    }
}
