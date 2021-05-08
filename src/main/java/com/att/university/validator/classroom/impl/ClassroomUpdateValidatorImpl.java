package com.att.university.validator.classroom.impl;

import com.att.university.request.classroom.ClassroomUpdateRequest;
import com.att.university.validator.classroom.ClassroomUpdateValidator;
import org.springframework.stereotype.Component;

@Component
public class ClassroomUpdateValidatorImpl implements ClassroomUpdateValidator {
    @Override
    public void validate(ClassroomUpdateRequest updateRequest) {
        validateNull(updateRequest.getBuildingId(), "Building id is null");

        validateNull(updateRequest.getNumber(), "Classroom number address is null");

        validateNull(updateRequest.getId(), "Classroom id is null");
    }
}
