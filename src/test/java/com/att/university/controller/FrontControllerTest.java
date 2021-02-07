package com.att.university.controller;

import com.att.university.dao.AcademicRankDao;
import com.att.university.dao.BuildingDao;
import com.att.university.dao.GroupDao;
import com.att.university.dao.ScienceDegreeDao;
import com.att.university.dao.StudentDao;
import com.att.university.dao.TeacherDao;
import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import com.att.university.entity.Student;
import com.att.university.view.ApplicationView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class FrontControllerTest {
    @Mock
    private BuildingDao buildingDao;

    @Mock
    private StudentDao studentDao;

    @Mock
    private TeacherDao teacherDao;

    @Mock
    private GroupDao groupDao;

    @Mock
    private ScienceDegreeDao scienceDegreeDao;

    @Mock
    private AcademicRankDao academicRankDao;

    @Mock
    private ApplicationView view;

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
        Faculty faculty = new Faculty(1, "test");

        List<Group> groups = Arrays.asList(
                new Group(1, "gf", faculty),
                new Group(1, "gf", faculty)
        );

        when(view.readIntValue()).thenReturn(1, 1, 0);
        when(view.readStringValue()).thenReturn("name", "lastName", "email", "password");
        when(groupDao.findAll(anyInt(), anyInt())).thenReturn(groups);

        frontController.runApplication();

        verify(studentDao).save(any(Student.class));
        verify(groupDao).findAll(anyInt(), anyInt());
        verify(view, times(3)).readIntValue();
        verify(view, times(4)).readStringValue();
        verify(view, times(9)).printMessage(anyString());
        verify(view, times(1)).printMessage(anyList());
    }
}
