package com.att.university.validator.academic_rank;

import com.att.university.request.academic_rank.AcademicRankUpdateRequest;
import com.att.university.validator.academic_rank.impl.AcademicRankUpdateValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AcademicRankUpdateValidatorTest {
    @Test
    void validatorShouldInvokeAllMethods() {
        final AcademicRankUpdateRequest updateRequest = new AcademicRankUpdateRequest(1, "test");

        AcademicRankUpdateValidator updateValidator = spy(new AcademicRankUpdateValidatorImpl());
        doNothing().when(updateValidator).validateNull(any(Object.class), anyString());
        doNothing().when(updateValidator).validateEmpty(anyString(), anyString());

        updateValidator.validate(updateRequest);

        verify(updateValidator, times(2)).validateNull(any(Object.class), anyString());
        verify(updateValidator, times(1)).validateEmpty(anyString(), anyString());
    }
}
