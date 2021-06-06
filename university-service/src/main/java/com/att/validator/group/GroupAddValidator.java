package com.att.validator.group;

import com.att.request.group.GroupAddRequest;
import com.att.validator.Validator;

public interface GroupAddValidator extends Validator {
    void validate(GroupAddRequest addRequest);
}
