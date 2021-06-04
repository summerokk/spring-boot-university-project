package com.att.validator.building.impl;

import com.att.request.building.BuildingUpdateRequest;
import com.att.validator.building.BuildingUpdateValidator;
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
