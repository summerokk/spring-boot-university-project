package com.att.university.validator.person.impl;

import com.att.university.request.person.teacher.TeacherRegisterRequest;
import com.att.university.validator.person.TeacherRegisterValidator;
import org.springframework.stereotype.Component;

@Component
public class TeacherRegisterValidatorImpl extends AbstractPersonValidatorImpl<TeacherRegisterRequest>
        implements TeacherRegisterValidator {
    @Override
    public void validate(TeacherRegisterRequest registerRequest) {
        baseInfoValidate(registerRequest);

        if (registerRequest.getLinkedin() == null) {
            throw new RuntimeException("Linkedin is null");
        }

        if (registerRequest.getAcademicRankId() == null) {
            throw new RuntimeException("Academic Rank Id is null");
        }

        if (registerRequest.getScienceDegreeId() == null) {
            throw new RuntimeException("Science Degree Id is null");
        }
    }
}
