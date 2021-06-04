package com.att.validator.group.impl;

import com.att.request.group.GroupUpdateRequest;
import com.att.validator.group.GroupUpdateValidator;
import org.springframework.stereotype.Component;

@Component
public class GroupUpdateValidatorImpl implements GroupUpdateValidator {
    @Override
    public void validate(GroupUpdateRequest updateRequest) {
        validateNull(updateRequest.getFacultyId(), "Faculty Id is null");

        validateNull(updateRequest.getId(), "Group Id is null");

        validateNull(updateRequest.getName(), "Group name is null");

        validateEmpty(updateRequest.getName(), "Group name is empty");
    }
}
