package com.att.validator.academic_rank;

import com.att.request.academic_rank.AcademicRankAddRequest;
import com.att.validator.academic_rank.impl.AcademicRankAddValidatorImpl;
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
class AcademicRankAddValidatorTest {
    @Test
    void validatorShouldInvokeAllMethods() {
        final AcademicRankAddRequest request = new AcademicRankAddRequest("test");

        AcademicRankAddValidator validator = Mockito.spy(new AcademicRankAddValidatorImpl());
        doNothing().when(validator).validateNull(any(Object.class), anyString());
        doNothing().when(validator).validateEmpty(anyString(), anyString());

        validator.validate(request);

        verify(validator, times(1)).validateNull(any(Object.class), anyString());
        verify(validator, times(1)).validateEmpty(anyString(), anyString());
    }
}
