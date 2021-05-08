package com.att.university.validator.building.impl;

import com.att.university.request.building.BuildingUpdateRequest;
import com.att.university.validator.building.BuildingUpdateValidator;
import org.springframework.stereotype.Component;

@Component
public class BuildingUpdateValidatorImpl implements BuildingUpdateValidator {
    @Override
    public void validate(BuildingUpdateRequest updateRequest) {
        validateNull(updateRequest.getAddress(), "Building address is null");

        validateEmpty(updateRequest.getAddress(), "Building address is empty");

        validateNull(updateRequest.getId(), "Building address id is null");
    }
}
