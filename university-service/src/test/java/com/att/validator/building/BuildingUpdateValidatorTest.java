package com.att.validator.building;

import com.att.request.building.BuildingUpdateRequest;
import com.att.validator.building.impl.BuildingUpdateValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BuildingUpdateValidatorTest {
    @Test
    void validatorShouldInvokeAllMethods() {
        final BuildingUpdateRequest updateRequest = new BuildingUpdateRequest(1, "test");

        BuildingUpdateValidator updateValidator = Mockito.spy(new BuildingUpdateValidatorImpl());
        doNothing().when(updateValidator).validateNull(any(Object.class), anyString());
        doNothing().when(updateValidator).validateEmpty(anyString(), anyString());

        updateValidator.validate(updateRequest);

        verify(updateValidator, times(2)).validateNull(any(Object.class), anyString());
        verify(updateValidator, times(1)).validateEmpty(anyString(), anyString());
    }
}