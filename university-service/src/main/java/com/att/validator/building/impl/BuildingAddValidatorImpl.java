package com.att.validator.building.impl;

import com.att.request.building.BuildingAddRequest;
import com.att.validator.building.BuildingAddValidator;
import org.springframework.stereotype.Component;

@Component
public class BuildingAddValidatorImpl implements BuildingAddValidator {
    @Override
    public void validate(BuildingAddRequest addRequest) {
        validateNull(addRequest.getAddress(), "Building address name is null");

        validateEmpty(addRequest.getAddress(), "Building address is empty");
    }
}
