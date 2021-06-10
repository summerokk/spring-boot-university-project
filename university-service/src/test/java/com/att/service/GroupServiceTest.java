package com.att.service;

import com.att.dao.FacultyRepository;
import com.att.dao.GroupRepository;
import com.att.entity.Faculty;
import com.att.entity.Group;
import com.att.exception.dao.FacultyNotFoundException;
import com.att.exception.dao.GroupNotFoundException;
import com.att.mapper.group.GroupAddRequestMapper;
import com.att.mapper.group.GroupUpdateRequestMapper;
import com.att.request.group.GroupAddRequest;
import com.att.request.group.GroupUpdateRequest;
import com.att.service.impl.GroupServiceImpl;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
    @Mock
    private GroupRepository groupRepository;

    @Mock
    private FacultyRepository facultyRepository;

    @Mock
    private GroupAddRequestMapper addRequestMapper;

    @Mock
    private GroupUpdateRequestMapper updateRequestMapper;

    @InjectMocks
    private GroupServiceImpl groupService;

    @Test
    void findAllShouldNotThrowException() {
        when(groupRepository.findAll()).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> groupService.findAll());

        verify(groupRepository).findAll();
    }

    @Test
    void updateCourseShouldThrowNotFoundExceptionIfGroupNotFound() {
        final GroupUpdateRequest request = generateUpdateRequest();

        when(groupRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(GroupNotFoundException.class, () -> groupService.update(request));

        verify(groupRepository).findById(anyInt());
        verifyNoMoreInteractions(groupRepository);
    }

    @Test
    void updateCourseShouldThrowNotFoundExceptionIfFacultyNotFound() {
        final GroupUpdateRequest request = generateUpdateRequest();
        final Group group = generateGroups().get(0);

        when(groupRepository.findById(anyInt())).thenReturn(Optional.of(group));
        when(facultyRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(FacultyNotFoundException.class, () -> groupService.update(request));

        verify(groupRepository).findById(anyInt());
        verify(facultyRepository).findById(anyInt());
        verifyNoMoreInteractions(groupRepository, facultyRepository);
    }

    @Test
    void updateCourseShouldNotThrowExceptionIfCourseIsFound() {
        final GroupUpdateRequest request = generateUpdateRequest();
        final Group group = generateGroups().get(0);

        when(groupRepository.findById(anyInt())).thenReturn(Optional.of(group));
        when(facultyRepository.findById(anyInt())).thenReturn(Optional.of(group.getFaculty()));
        when(updateRequestMapper.convertToEntity(any(GroupUpdateRequest.class), any(Faculty.class))).thenReturn(group);

        groupService.update(request);

        verify(groupRepository).findById(anyInt());
        verify(facultyRepository).findById(anyInt());
        verify(groupRepository).save(any(Group.class));
        verifyNoMoreInteractions(groupRepository);
    }

    @Test
    void findByIdSShouldThrowNotFoundExceptionIfCourseNotFound() {
        Integer id = 4;

        when(groupRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(GroupNotFoundException.class, () -> groupService.findById(id));

        verify(groupRepository).findById(anyInt());
        verifyNoMoreInteractions(groupRepository);
    }

    @Test
    void findByIdShouldReturnEntityWhenCourseExists() {
        Integer id = 4;

        when(groupRepository.findById(anyInt())).thenReturn(Optional.of(generateGroups().get(0)));

        groupService.findById(id);

        verify(groupRepository).findById(anyInt());
        verifyNoMoreInteractions(groupRepository);
    }

    @Test
    void createCourseShouldNotThrowException() {
        final GroupAddRequest request = generateAddRequest();
        final Group group = generateGroups().get(0);

        when(addRequestMapper.convertToEntity(any(GroupAddRequest.class), any(Faculty.class))).thenReturn(group);
        when(facultyRepository.findById(anyInt())).thenReturn(Optional.of(group.getFaculty()));

        groupService.create(request);

        verify(groupRepository).save(any(Group.class));
        verify(facultyRepository).findById(anyInt());
        verifyNoMoreInteractions(groupRepository);
    }

    @Test
    void createCourseShouldThrowExceptionIfFacultyNotFound() {
        final GroupAddRequest request = generateAddRequest();

        when(facultyRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(FacultyNotFoundException.class, () -> groupService.create(request));

        verify(facultyRepository).findById(anyInt());
        verifyNoMoreInteractions(facultyRepository);
    }

    @Test
    void deleteCourseShouldThrowNotFoundExceptionIfCourseNotFound() {
        when(groupRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(GroupNotFoundException.class, () -> groupService.deleteById(1));

        verify(groupRepository).findById(anyInt());
        verifyNoMoreInteractions(groupRepository);
    }

    @Test
    void deleteCourseShouldNotThrowExceptionIfCourseIsFound() {
        when(groupRepository.findById(anyInt())).thenReturn(Optional.of(generateGroups().get(0)));

        groupService.deleteById(1);

        verify(groupRepository).findById(anyInt());
        verify(groupRepository).deleteById(anyInt());
        verifyNoMoreInteractions(groupRepository);
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
