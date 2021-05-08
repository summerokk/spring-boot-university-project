package com.att.university.validator.group;

import com.att.university.request.group.GroupUpdateRequest;
import com.att.university.validator.Validator;

public interface GroupUpdateValidator extends Validator {
    void validate(GroupUpdateRequest updateRequest);
}
