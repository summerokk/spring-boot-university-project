package com.att.university.validator.building;

import com.att.university.request.building.BuildingAddRequest;
import com.att.university.validator.Validator;

public interface BuildingAddValidator extends Validator {
    void validate(BuildingAddRequest addRequest);
}
