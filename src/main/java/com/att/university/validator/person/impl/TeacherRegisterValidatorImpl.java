package com.att.university.validator.person.impl;

import com.att.university.request.person.teacher.TeacherRegisterRequest;
import com.att.university.validator.person.TeacherRegisterValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TeacherRegisterValidatorImpl extends AbstractPersonValidatorImpl<TeacherRegisterRequest>
        implements TeacherRegisterValidator {
    private final int minPasswordLength;

    public TeacherRegisterValidatorImpl(@Value("${password.min.length}") int minPasswordLength) {
        this.minPasswordLength = minPasswordLength;
    }

    @Override
    public void validate(TeacherRegisterRequest registerRequest) {
        baseInfoValidate(registerRequest);

        validateNull(registerRequest.getPassword(), "Password is null");
        validateNull(registerRequest.getLinkedin(), "Linkedin is null");
        validateNull(registerRequest.getAcademicRankId(), "Academic Rank Id is null");
        validateNull(registerRequest.getScienceDegreeId(), "Science Degree Id is null");

        validatePasswordLength(registerRequest.getPassword(), minPasswordLength);
        validatePasswordEquality(registerRequest.getPassword(), registerRequest.getPasswordConfirm());
    }
}
