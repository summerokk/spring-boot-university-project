package com.att.university.service;

import com.att.university.dao.FacultyDao;
import com.att.university.dao.GroupDao;
import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import com.att.university.exception.dao.FacultyNotFoundException;
import com.att.university.exception.dao.GroupNotFoundException;
import com.att.university.mapper.group.GroupAddRequestMapper;
import com.att.university.mapper.group.GroupUpdateRequestMapper;
import com.att.university.request.group.GroupAddRequest;
import com.att.university.request.group.GroupUpdateRequest;
import com.att.university.service.impl.GroupServiceImpl;
import com.att.university.validator.group.GroupAddValidator;
import com.att.university.validator.group.GroupUpdateValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
    @Mock
    private GroupDao groupDao;

    @Mock
    private FacultyDao facultyDao;

    @Mock
    private GroupUpdateValidator updateValidator;

    @Mock
    private GroupAddValidator addValidator;

    @Mock
    private GroupAddRequestMapper addRequestMapper;

    @Mock
    private GroupUpdateRequestMapper updateRequestMapper;

    @InjectMocks
    private GroupServiceImpl groupService;

    @Test
    void findAllShouldNotThrowException() {
        when(groupDao.count()).thenReturn(2);
        when(groupDao.findAll(anyInt(), anyInt())).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> groupService.findAll());

        verify(groupDao).findAll(anyInt(), anyInt());
    }

    @Test
    void updateCourseShouldThrowNotFoundExceptionIfGroupNotFound() {
        final GroupUpdateRequest request = generateUpdateRequest();

        doNothing().when(updateValidator).validate(any(GroupUpdateRequest.class));
        when(groupDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(GroupNotFoundException.class, () -> groupService.update(request));

        verify(updateValidator).validate(any(GroupUpdateRequest.class));
        verify(groupDao).findById(anyInt());
        verifyNoMoreInteractions(groupDao, updateValidator);
    }

    @Test
    void updateCourseShouldThrowNotFoundExceptionIfFacultyNotFound() {
        final GroupUpdateRequest request = generateUpdateRequest();
        final Group group = generateGroups().get(0);

        doNothing().when(updateValidator).validate(any(GroupUpdateRequest.class));
        when(groupDao.findById(anyInt())).thenReturn(Optional.of(group));
        when(facultyDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(FacultyNotFoundException.class, () -> groupService.update(request));

        verify(updateValidator).validate(any(GroupUpdateRequest.class));
        verify(groupDao).findById(anyInt());
        verify(facultyDao).findById(anyInt());
        verifyNoMoreInteractions(groupDao, facultyDao, updateValidator);
    }

    @Test
    void updateCourseShouldNotThrowExceptionIfCourseIsFound() {
        final GroupUpdateRequest request = generateUpdateRequest();
        final Group group = generateGroups().get(0);

        doNothing().when(updateValidator).validate(any(GroupUpdateRequest.class));
        when(groupDao.findById(anyInt())).thenReturn(Optional.of(group));
        when(facultyDao.findById(anyInt())).thenReturn(Optional.of(group.getFaculty()));
        when(updateRequestMapper.convertToEntity(any(GroupUpdateRequest.class), any(Faculty.class))).thenReturn(group);

        groupService.update(request);

        verify(updateValidator).validate(any(GroupUpdateRequest.class));
        verify(groupDao).findById(anyInt());
        verify(facultyDao).findById(anyInt());
        verify(groupDao).update(any(Group.class));
        verifyNoMoreInteractions(groupDao, updateValidator);
    }

    @Test
    void findByIdSShouldThrowNotFoundExceptionIfCourseNotFound() {
        Integer id = 4;

        when(groupDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(GroupNotFoundException.class, () -> groupService.findById(id));

        verify(groupDao).findById(anyInt());
        verifyNoMoreInteractions(groupDao);
    }

    @Test
    void findByIdShouldReturnEntityWhenCourseExists() {
        Integer id = 4;

        when(groupDao.findById(anyInt())).thenReturn(Optional.of(generateGroups().get(0)));

        groupService.findById(id);

        verify(groupDao).findById(anyInt());
        verifyNoMoreInteractions(groupDao);
    }

    @Test
    void createCourseShouldNotThrowException() {
        final GroupAddRequest request = generateAddRequest();
        final Group group = generateGroups().get(0);

        doNothing().when(addValidator).validate(any(GroupAddRequest.class));
        when(addRequestMapper.convertToEntity(any(GroupAddRequest.class), any(Faculty.class))).thenReturn(group);
        when(facultyDao.findById(anyInt())).thenReturn(Optional.of(group.getFaculty()));

        groupService.create(request);

        verify(addValidator).validate(any(GroupAddRequest.class));
        verify(groupDao).save(any(Group.class));
        verify(facultyDao).findById(anyInt());
        verifyNoMoreInteractions(groupDao, addValidator);
    }

    @Test
    void createCourseShouldThrowExceptionIfFacultyNotFound() {
        final GroupAddRequest request = generateAddRequest();

        doNothing().when(addValidator).validate(any(GroupAddRequest.class));
        when(facultyDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(FacultyNotFoundException.class, () -> groupService.create(request));

        verify(addValidator).validate(any(GroupAddRequest.class));
        verify(facultyDao).findById(anyInt());
        verifyNoMoreInteractions(facultyDao, addValidator);
    }

    @Test
    void deleteCourseShouldThrowNotFoundExceptionIfCourseNotFound() {
        when(groupDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(GroupNotFoundException.class, () -> groupService.deleteById(1));

        verify(groupDao).findById(anyInt());
        verifyNoMoreInteractions(groupDao);
    }

    @Test
    void deleteCourseShouldNotThrowExceptionIfCourseIsFound() {
        when(groupDao.findById(anyInt())).thenReturn(Optional.of(generateGroups().get(0)));

        groupService.deleteById(1);

        verify(groupDao).findById(anyInt());
        verify(groupDao).deleteById(anyInt());
        verifyNoMoreInteractions(groupDao);
    }

    private List<Group> generateGroups() {
        return Arrays.asList(
                new Group(null, "GT-23", new Faculty(1, "School of Visual arts")),
                new Group(null, "HT-22", new Faculty(2, "Department of Geography"))
        );
    }

    private GroupAddRequest generateAddRequest() {
        return new GroupAddRequest("new", 1);
    }

    private GroupUpdateRequest generateUpdateRequest() {
        return new GroupUpdateRequest(1, "update", 1);
    }
}
