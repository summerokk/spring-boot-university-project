package com.att.university.validator.person.impl;

import com.att.university.request.person.teacher.TeacherRegisterRequest;
import com.att.university.validator.person.TeacherRegisterValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TeacherRegisterValidatorImpl extends AbstractPersonValidatorImpl<TeacherRegisterRequest>
        implements TeacherRegisterValidator {
    public TeacherRegisterValidatorImpl(@Value("${password.min.length}") int minPasswordLength) {
        super(minPasswordLength);
    }

    @Override
    public void validate(TeacherRegisterRequest registerRequest) {
        baseInfoValidate(registerRequest);

        validateNull(registerRequest.getLinkedin(), "Linkedin is null");
        validateNull(registerRequest.getAcademicRankId(), "Academic Rank Id is null");
        validateNull(registerRequest.getScienceDegreeId(), "Science Degree Id is null");
    }
}
