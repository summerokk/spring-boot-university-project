package com.att.validator.science_degree;

import com.att.request.science_degree.ScienceDegreeUpdateRequest;
import com.att.validator.science_degree.impl.ScienceDegreeUpdateValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ScienceDegreeUpdateValidatorTest {
    @Test
    void validatorShouldInvokeAllMethods() {
        final ScienceDegreeUpdateRequest updateRequest = new ScienceDegreeUpdateRequest(1, "test");

        ScienceDegreeUpdateValidator updateValidator = Mockito.spy(new ScienceDegreeUpdateValidatorImpl());
        doNothing().when(updateValidator).validateNull(any(Object.class), anyString());
        doNothing().when(updateValidator).validateEmpty(anyString(), anyString());

        updateValidator.validate(updateRequest);

        verify(updateValidator, times(2)).validateNull(any(Object.class), anyString());
        verify(updateValidator, times(1)).validateEmpty(anyString(), anyString());
    }
}
