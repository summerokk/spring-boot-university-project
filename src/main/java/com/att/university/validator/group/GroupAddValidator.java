package com.att.university.validator.group;

import com.att.university.request.group.GroupAddRequest;
import com.att.university.validator.Validator;

public interface GroupAddValidator extends Validator {
    void validate(GroupAddRequest addRequest);
}
