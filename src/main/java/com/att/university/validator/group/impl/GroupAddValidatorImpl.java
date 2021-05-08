package com.att.university.validator.group.impl;

import com.att.university.request.group.GroupAddRequest;
import com.att.university.validator.group.GroupAddValidator;
import org.springframework.stereotype.Component;

@Component
public class GroupAddValidatorImpl implements GroupAddValidator {
    @Override
    public void validate(GroupAddRequest addRequest) {
        validateNull(addRequest.getFacultyId(), "Faculty Id is null");

        validateNull(addRequest.getName(), "Group name is null");

        validateEmpty(addRequest.getName(), "Group name is empty");
    }
}
