package com.att.service;

import com.att.dao.BuildingRepository;
import com.att.dao.ClassroomRepository;
import com.att.entity.Building;
import com.att.entity.Classroom;
import com.att.exception.dao.BuildingNotFoundException;
import com.att.exception.dao.ClassroomNotFoundException;
import com.att.mapper.classroom.ClassroomAddRequestMapper;
import com.att.mapper.classroom.ClassroomUpdateRequestMapper;
import com.att.request.classroom.ClassroomAddRequest;
import com.att.request.classroom.ClassroomUpdateRequest;
import com.att.service.impl.ClassroomServiceImpl;
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
class ClassroomServiceTest {
    @Mock
    private ClassroomRepository classroomRepository;

    @Mock
    private BuildingRepository buildingRepository;

    @Mock
    private ClassroomAddRequestMapper addRequestMapper;

    @Mock
    private ClassroomUpdateRequestMapper updateRequestMapper;

    @InjectMocks
    private ClassroomServiceImpl classroomService;

    @Test
    void findAllShouldNotThrowException() {
        when(classroomService.findAll()).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> classroomService.findAll());

        verify(classroomRepository).findAll();
    }

    @Test
    void updateCourseShouldThrowNotFoundExceptionIfClassroomNotFound() {
        final ClassroomUpdateRequest request = generateUpdateRequest();

        when(classroomRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ClassroomNotFoundException.class, () -> classroomService.update(request));

        verify(classroomRepository).findById(anyInt());
        verifyNoMoreInteractions(classroomRepository);
    }

    @Test
    void updateCourseShouldThrowNotFoundExceptionIfBuildingNotFound() {
        final ClassroomUpdateRequest request = generateUpdateRequest();
        final Classroom Classroom = generateClassrooms().get(0);

        when(classroomRepository.findById(anyInt())).thenReturn(Optional.of(Classroom));
        when(buildingRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(BuildingNotFoundException.class, () -> classroomService.update(request));

        verify(classroomRepository).findById(anyInt());
        verify(buildingRepository).findById(anyInt());
        verifyNoMoreInteractions(classroomRepository, buildingRepository);
    }

    @Test
    void updateCourseShouldNotThrowExceptionIfCourseIsFound() {
        final ClassroomUpdateRequest request = generateUpdateRequest();
        final Classroom Classroom = generateClassrooms().get(0);

        when(classroomRepository.findById(anyInt())).thenReturn(Optional.of(Classroom));
        when(buildingRepository.findById(anyInt())).thenReturn(Optional.of(Classroom.getBuilding()));
        when(updateRequestMapper.convertToEntity(any(ClassroomUpdateRequest.class), any(Building.class))).thenReturn(Classroom);

        classroomService.update(request);

        verify(classroomRepository).findById(anyInt());
        verify(buildingRepository).findById(anyInt());
        verify(classroomRepository).save(any(Classroom.class));
        verifyNoMoreInteractions(classroomRepository);
    }

    @Test
    void findByIdSShouldThrowNotFoundExceptionIfCourseNotFound() {
        Integer id = 4;

        when(classroomRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ClassroomNotFoundException.class, () -> classroomService.findById(id));

        verify(classroomRepository).findById(anyInt());
        verifyNoMoreInteractions(classroomRepository);
    }

    @Test
    void findByIdShouldReturnEntityWhenCourseExists() {
        Integer id = 4;

        when(classroomRepository.findById(anyInt())).thenReturn(Optional.of(generateClassrooms().get(0)));

        classroomService.findById(id);

        verify(classroomRepository).findById(anyInt());
        verifyNoMoreInteractions(classroomRepository);
    }

    @Test
    void createCourseShouldNotThrowException() {
        final ClassroomAddRequest request = generateAddRequest();
        final Classroom Classroom = generateClassrooms().get(0);

        when(addRequestMapper.convertToEntity(any(ClassroomAddRequest.class), any(Building.class))).thenReturn(Classroom);
        when(buildingRepository.findById(anyInt())).thenReturn(Optional.of(Classroom.getBuilding()));

        classroomService.create(request);

        verify(classroomRepository).save(any(Classroom.class));
        verify(buildingRepository).findById(anyInt());
        verifyNoMoreInteractions(classroomRepository);
    }

    @Test
    void createCourseShouldThrowExceptionIfBuildingNotFound() {
        final ClassroomAddRequest request = generateAddRequest();

        when(buildingRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(BuildingNotFoundException.class, () -> classroomService.create(request));

        verify(buildingRepository).findById(anyInt());
        verifyNoMoreInteractions(buildingRepository);
    }

    @Test
    void deleteCourseShouldThrowNotFoundExceptionIfCourseNotFound() {
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ClassroomNotFoundException.class, () -> classroomService.deleteById(1));

        verify(classroomRepository).findById(anyInt());
        verifyNoMoreInteractions(classroomRepository);
    }

    @Test
    void deleteCourseShouldNotThrowExceptionIfCourseIsFound() {
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.of(generateClassrooms().get(0)));

        classroomService.deleteById(1);

        verify(classroomRepository).findById(anyInt());
        verify(classroomRepository).deleteById(anyInt());
        verifyNoMoreInteractions(classroomRepository);
    }

    private List<Classroom> generateClassrooms() {
        return Arrays.asList(
                new Classroom(null, 42, new Building(1, "Lokova 13")),
                new Classroom(null, 13, new Building(2, "Steamova 43"))
        );
    }

    private ClassroomAddRequest generateAddRequest() {
        return new ClassroomAddRequest(12, 1);
    }

    private ClassroomUpdateRequest generateUpdateRequest() {
        return new ClassroomUpdateRequest(1, 12, 1);
    }
}
