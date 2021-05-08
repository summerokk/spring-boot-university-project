package com.att.university.service;

import com.att.university.dao.BuildingDao;
import com.att.university.dao.ClassroomDao;
import com.att.university.entity.Building;
import com.att.university.entity.Classroom;
import com.att.university.exception.dao.BuildingNotFoundException;
import com.att.university.exception.dao.ClassroomNotFoundException;
import com.att.university.mapper.classroom.ClassroomAddRequestMapper;
import com.att.university.mapper.classroom.ClassroomUpdateRequestMapper;
import com.att.university.request.classroom.ClassroomAddRequest;
import com.att.university.request.classroom.ClassroomUpdateRequest;
import com.att.university.service.impl.ClassroomServiceImpl;
import com.att.university.validator.classroom.ClassroomAddValidator;
import com.att.university.validator.classroom.ClassroomUpdateValidator;
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
class ClassroomServiceTest {
    @Mock
    private ClassroomDao classroomDao;

    @Mock
    private BuildingDao buildingDao;

    @Mock
    private ClassroomUpdateValidator updateValidator;

    @Mock
    private ClassroomAddValidator addValidator;

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

        verify(classroomDao).findAll(anyInt(), anyInt());
    }

    @Test
    void updateCourseShouldThrowNotFoundExceptionIfClassroomNotFound() {
        final ClassroomUpdateRequest request = generateUpdateRequest();

        doNothing().when(updateValidator).validate(any(ClassroomUpdateRequest.class));
        when(classroomDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ClassroomNotFoundException.class, () -> classroomService.update(request));

        verify(updateValidator).validate(any(ClassroomUpdateRequest.class));
        verify(classroomDao).findById(anyInt());
        verifyNoMoreInteractions(classroomDao, updateValidator);
    }

    @Test
    void updateCourseShouldThrowNotFoundExceptionIfBuildingNotFound() {
        final ClassroomUpdateRequest request = generateUpdateRequest();
        final Classroom Classroom = generateClassrooms().get(0);

        doNothing().when(updateValidator).validate(any(ClassroomUpdateRequest.class));
        when(classroomDao.findById(anyInt())).thenReturn(Optional.of(Classroom));
        when(buildingDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(BuildingNotFoundException.class, () -> classroomService.update(request));

        verify(updateValidator).validate(any(ClassroomUpdateRequest.class));
        verify(classroomDao).findById(anyInt());
        verify(buildingDao).findById(anyInt());
        verifyNoMoreInteractions(classroomDao, buildingDao, updateValidator);
    }

    @Test
    void updateCourseShouldNotThrowExceptionIfCourseIsFound() {
        final ClassroomUpdateRequest request = generateUpdateRequest();
        final Classroom Classroom = generateClassrooms().get(0);

        doNothing().when(updateValidator).validate(any(ClassroomUpdateRequest.class));
        when(classroomDao.findById(anyInt())).thenReturn(Optional.of(Classroom));
        when(buildingDao.findById(anyInt())).thenReturn(Optional.of(Classroom.getBuilding()));
        when(updateRequestMapper.convertToEntity(any(ClassroomUpdateRequest.class), any(Building.class))).thenReturn(Classroom);

        classroomService.update(request);

        verify(updateValidator).validate(any(ClassroomUpdateRequest.class));
        verify(classroomDao).findById(anyInt());
        verify(buildingDao).findById(anyInt());
        verify(classroomDao).update(any(Classroom.class));
        verifyNoMoreInteractions(classroomDao, updateValidator);
    }

    @Test
    void findByIdSShouldThrowNotFoundExceptionIfCourseNotFound() {
        Integer id = 4;

        when(classroomDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ClassroomNotFoundException.class, () -> classroomService.findById(id));

        verify(classroomDao).findById(anyInt());
        verifyNoMoreInteractions(classroomDao);
    }

    @Test
    void findByIdShouldReturnEntityWhenCourseExists() {
        Integer id = 4;

        when(classroomDao.findById(anyInt())).thenReturn(Optional.of(generateClassrooms().get(0)));

        classroomService.findById(id);

        verify(classroomDao).findById(anyInt());
        verifyNoMoreInteractions(classroomDao);
    }

    @Test
    void createCourseShouldNotThrowException() {
        final ClassroomAddRequest request = generateAddRequest();
        final Classroom Classroom = generateClassrooms().get(0);

        doNothing().when(addValidator).validate(any(ClassroomAddRequest.class));
        when(addRequestMapper.convertToEntity(any(ClassroomAddRequest.class), any(Building.class))).thenReturn(Classroom);
        when(buildingDao.findById(anyInt())).thenReturn(Optional.of(Classroom.getBuilding()));

        classroomService.create(request);

        verify(addValidator).validate(any(ClassroomAddRequest.class));
        verify(classroomDao).save(any(Classroom.class));
        verify(buildingDao).findById(anyInt());
        verifyNoMoreInteractions(classroomDao, addValidator);
    }

    @Test
    void createCourseShouldThrowExceptionIfBuildingNotFound() {
        final ClassroomAddRequest request = generateAddRequest();

        doNothing().when(addValidator).validate(any(ClassroomAddRequest.class));
        when(buildingDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(BuildingNotFoundException.class, () -> classroomService.create(request));

        verify(addValidator).validate(any(ClassroomAddRequest.class));
        verify(buildingDao).findById(anyInt());
        verifyNoMoreInteractions(buildingDao, addValidator);
    }

    @Test
    void deleteCourseShouldThrowNotFoundExceptionIfCourseNotFound() {
        when(classroomDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ClassroomNotFoundException.class, () -> classroomService.deleteById(1));

        verify(classroomDao).findById(anyInt());
        verifyNoMoreInteractions(classroomDao);
    }

    @Test
    void deleteCourseShouldNotThrowExceptionIfCourseIsFound() {
        when(classroomDao.findById(anyInt())).thenReturn(Optional.of(generateClassrooms().get(0)));

        classroomService.deleteById(1);

        verify(classroomDao).findById(anyInt());
        verify(classroomDao).deleteById(anyInt());
        verifyNoMoreInteractions(classroomDao);
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
