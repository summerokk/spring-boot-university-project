package com.att.university.service;

import com.att.university.dao.CourseDao;
import com.att.university.entity.Course;
import com.att.university.exception.dao.CourseNotFoundException;
import com.att.university.mapper.course.CourseAddRequestMapper;
import com.att.university.mapper.course.CourseUpdateRequestMapper;
import com.att.university.request.course.CourseAddRequest;
import com.att.university.request.course.CourseUpdateRequest;
import com.att.university.service.impl.CourseServiceImpl;
import com.att.university.validator.course.CourseAddValidator;
import com.att.university.validator.course.CourseUpdateValidator;
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
class CourseServiceTest {
    @Mock
    private CourseDao courseDao;

    @Mock
    private CourseUpdateValidator updateValidator;

    @Mock
    private CourseAddValidator addValidator;

    @Mock
    private CourseAddRequestMapper addRequestMapper;

    @Mock
    private CourseUpdateRequestMapper updateRequestMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    void updateCourseShouldThrowNotFoundExceptionIfCourseNotFound() {
        final CourseUpdateRequest request = new CourseUpdateRequest(1, "update");
        final Course course = generateCourses().get(0);

        doNothing().when(updateValidator).validate(any(CourseUpdateRequest.class));
        when(courseDao.findById(course.getId())).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.update(request));

        verify(updateValidator).validate(any(CourseUpdateRequest.class));
        verify(courseDao).findById(anyInt());
        verifyNoMoreInteractions(courseDao, updateValidator);
    }

    @Test
    void updateCourseShouldNotThrowExceptionIfCourseIsFound() {
        final CourseUpdateRequest request = new CourseUpdateRequest(1, "update");
        final Course course = generateCourses().get(0);

        doNothing().when(updateValidator).validate(any(CourseUpdateRequest.class));
        when(courseDao.findById(course.getId())).thenReturn(Optional.of(generateCourses().get(0)));
        when(updateRequestMapper.convertToEntity(any(CourseUpdateRequest.class))).thenReturn((generateCourses().get(0)));

        courseService.update(request);

        verify(updateValidator).validate(any(CourseUpdateRequest.class));
        verify(courseDao).findById(anyInt());
        verify(courseDao).update(any(Course.class));
        verifyNoMoreInteractions(courseDao, updateValidator);
    }

    @Test
    void findAllShouldNotThrowException() {
        when(courseDao.findAll(anyInt(), anyInt())).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> courseService.findAll());

        verify(courseDao).findAll(anyInt(), anyInt());
    }


    @Test
    void findByIdSShouldThrowNotFoundExceptionIfCourseNotFound() {
        Integer id = 4;

        when(courseDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.findById(id));

        verify(courseDao).findById(anyInt());
        verifyNoMoreInteractions(courseDao);
    }

    @Test
    void findByIdShouldReturnEntityWhenCourseExists() {
        Integer id = 4;

        when(courseDao.findById(anyInt())).thenReturn(Optional.of(generateCourses().get(0)));

        courseService.findById(id);

        verify(courseDao).findById(anyInt());
        verifyNoMoreInteractions(courseDao);
    }

    @Test
    void createCourseShouldNotThrowException() {
        final CourseAddRequest request = new CourseAddRequest("new");
        doNothing().when(addValidator).validate(any(CourseAddRequest.class));
        when(addRequestMapper.convertToEntity(request)).thenReturn(generateCourses().get(0));

        courseService.create(request);

        verify(addValidator).validate(any(CourseAddRequest.class));
        verify(courseDao).save(any(Course.class));
        verifyNoMoreInteractions(courseDao, addValidator);
    }

    @Test
    void deleteCourseShouldThrowNotFoundExceptionIfCourseNotFound() {
        when(courseDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.deleteById(1));

        verify(courseDao).findById(anyInt());
        verifyNoMoreInteractions(courseDao);
    }

    @Test
    void deleteCourseShouldNotThrowExceptionIfCourseIsFound() {
        when(courseDao.findById(anyInt())).thenReturn(Optional.of(generateCourses().get(0)));

        courseService.deleteById(1);

        verify(courseDao).findById(anyInt());
        verify(courseDao).deleteById(anyInt());
        verifyNoMoreInteractions(courseDao);
    }

    private List<Course> generateCourses() {
        return Arrays.asList(
                new Course(1, "Special Topics in Agronomy"),
                new Course(2, "Math"),
                new Course(3, "Biology")
        );
    }
}
