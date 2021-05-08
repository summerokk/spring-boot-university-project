package com.att.university.validator.science_degree;

import com.att.university.request.science_degree.ScienceDegreeAddRequest;
import com.att.university.validator.science_degree.impl.ScienceDegreeAddValidatorImpl;
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
class ScienceDegreeAddValidatorTest {
    @Test
    void validatorShouldInvokeAllMethods() {
        final ScienceDegreeAddRequest request = new ScienceDegreeAddRequest("test");

        ScienceDegreeAddValidator validator = spy(new ScienceDegreeAddValidatorImpl());
        doNothing().when(validator).validateNull(any(Object.class), anyString());
        doNothing().when(validator).validateEmpty(anyString(), anyString());

        validator.validate(request);

        verify(validator, times(1)).validateNull(any(Object.class), anyString());
        verify(validator, times(1)).validateEmpty(anyString(), anyString());
    }
}
