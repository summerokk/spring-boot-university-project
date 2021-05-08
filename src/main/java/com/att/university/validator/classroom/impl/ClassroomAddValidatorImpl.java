package com.att.university.validator.classroom.impl;

import com.att.university.request.classroom.ClassroomAddRequest;
import com.att.university.validator.classroom.ClassroomAddValidator;
import org.springframework.stereotype.Component;

@Component
public class ClassroomAddValidatorImpl implements ClassroomAddValidator {
    @Override
    public void validate(ClassroomAddRequest addRequest) {
        validateNull(addRequest.getBuildingId(), "Building id is null");

        validateNull(addRequest.getNumber(), "Classroom number address is null");
    }
}
