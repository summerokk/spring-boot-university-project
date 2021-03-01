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

        validateNull(registerRequest.getLinkedin(), "Linkedin is null");
        validateNull(registerRequest.getAcademicRankId(), "Academic Rank Id is null");
        validateNull(registerRequest.getScienceDegreeId(), "Science Degree Id is null");
    }
}
