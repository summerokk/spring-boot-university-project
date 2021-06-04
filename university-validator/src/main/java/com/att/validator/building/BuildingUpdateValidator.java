package com.att.validator.building;

import com.att.request.building.BuildingUpdateRequest;
import com.att.validator.Validator;

public interface BuildingUpdateValidator extends Validator {
    void validate(BuildingUpdateRequest updateRequest);
}
