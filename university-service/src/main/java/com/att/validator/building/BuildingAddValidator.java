package com.att.validator.building;

import com.att.request.building.BuildingAddRequest;
import com.att.validator.Validator;

public interface BuildingAddValidator extends Validator {
    void validate(BuildingAddRequest addRequest);
}
