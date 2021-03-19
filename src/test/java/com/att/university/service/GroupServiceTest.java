package com.att.university.service;

import com.att.university.dao.GroupDao;
import com.att.university.service.impl.GroupServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
    @Mock
    private GroupDao groupDao;

    @InjectMocks
    private GroupServiceImpl groupService;

    @Test
    void findAllShouldNotThrowException() {
        when(groupService.findAll()).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> groupService.findAll());

        verify(groupDao).findAll(anyInt(), anyInt());
    }
}
