package com.att.validator.group;

import com.att.request.group.GroupUpdateRequest;
import com.att.validator.Validator;

public interface GroupUpdateValidator extends Validator {
    void validate(GroupUpdateRequest updateRequest);
}
