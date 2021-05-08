package com.att.university.validator.group;

import com.att.university.request.group.GroupAddRequest;
import com.att.university.request.group.GroupUpdateRequest;
import com.att.university.validator.group.impl.GroupAddValidatorImpl;
import com.att.university.validator.group.impl.GroupUpdateValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)

class GroupUpdateValidatorTest {
    @Test
    void validatorShouldInvokeAllMethods() {
        final GroupUpdateRequest request = new GroupUpdateRequest(1, "test", 1);

        GroupUpdateValidator validator = spy(new GroupUpdateValidatorImpl());
        doNothing().when(validator).validateNull(any(Object.class), anyString());
        doNothing().when(validator).validateEmpty(anyString(), anyString());

        validator.validate(request);

        verify(validator, times(3)).validateNull(any(Object.class), anyString());
        verify(validator, times(1)).validateEmpty(anyString(), anyString());
    }
}