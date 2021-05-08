package com.att.university.validator.building;

import com.att.university.request.building.BuildingUpdateRequest;
import com.att.university.validator.Validator;

public interface BuildingUpdateValidator extends Validator {
    void validate(BuildingUpdateRequest updateRequest);
}
