package com.att.university.controller;

import com.att.university.dao.GroupDao;
import com.att.university.dao.StudentDao;
import com.att.university.entity.Student;
import com.att.university.view.ApplicationView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class FrontControllerTest {
    @Mock
    private ApplicationView view;

    @Mock
    private StudentDao studentDao;

    @Mock
    private GroupDao groupDao;

    @InjectMocks
    private FrontController frontController;

    @Test
    void runCommandShouldReturnResultIfCommandDoesNotExist() {
        when(view.readIntValue()).thenReturn(111,0);
        frontController.runApplication();

        verify(view, times(4)).printMessage(anyString());
        verify(view, times(2)).printMessage(anyInt());
        verify(view, times(2)).readIntValue();
    }

    @Test
    void addStudentShouldReturnResult() {
        when(view.readIntValue()).thenReturn(1, 1, 0);
        when(view.readStringValue()).thenReturn("name", "lastName", "email", "password");

        frontController.runApplication();

        verify(studentDao).save(any(Student.class));
        verify(groupDao).findAll(anyInt(), anyInt());
        verify(view, times(3)).readIntValue();
        verify(view, times(4)).readStringValue();
        verify(view, times(9)).printMessage(anyString());
    }
}
