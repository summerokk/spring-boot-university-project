package com.att.service;

import com.att.dao.CourseRepository;
import com.att.entity.Course;
import com.att.exception.dao.CourseNotFoundException;
import com.att.mapper.course.CourseAddRequestMapper;
import com.att.mapper.course.CourseUpdateRequestMapper;
import com.att.request.course.CourseAddRequest;
import com.att.request.course.CourseUpdateRequest;
import com.att.service.impl.CourseServiceImpl;
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
class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;

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

        when(courseRepository.findById(course.getId())).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.update(request));

        verify(courseRepository).findById(anyInt());
    }

    @Test
    void updateCourseShouldNotThrowExceptionIfCourseIsFound() {
        final CourseUpdateRequest request = new CourseUpdateRequest(1, "update");
        final Course course = generateCourses().get(0);

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(generateCourses().get(0)));
        when(updateRequestMapper.convertToEntity(any(CourseUpdateRequest.class))).thenReturn((generateCourses().get(0)));

        courseService.update(request);

        verify(courseRepository).findById(anyInt());
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void findAllShouldNotThrowException() {
        when(courseRepository.findAll()).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> courseService.findAll());

        verify(courseRepository).findAll();
    }


    @Test
    void findByIdSShouldThrowNotFoundExceptionIfCourseNotFound() {
        Integer id = 4;

        when(courseRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.findById(id));

        verify(courseRepository).findById(anyInt());
        verifyNoMoreInteractions(courseRepository);
    }

    @Test
    void findByIdShouldReturnEntityWhenCourseExists() {
        Integer id = 4;

        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(generateCourses().get(0)));

        courseService.findById(id);

        verify(courseRepository).findById(anyInt());
        verifyNoMoreInteractions(courseRepository);
    }

    @Test
    void createCourseShouldNotThrowException() {
        final CourseAddRequest request = new CourseAddRequest("new");
        when(addRequestMapper.convertToEntity(request)).thenReturn(generateCourses().get(0));

        courseService.create(request);

        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void deleteCourseShouldThrowNotFoundExceptionIfCourseNotFound() {
        when(courseRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.deleteById(1));

        verify(courseRepository).findById(anyInt());
        verifyNoMoreInteractions(courseRepository);
    }

    @Test
    void deleteCourseShouldNotThrowExceptionIfCourseIsFound() {
        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(generateCourses().get(0)));

        courseService.deleteById(1);

        verify(courseRepository).findById(anyInt());
        verify(courseRepository).deleteById(anyInt());
        verifyNoMoreInteractions(courseRepository);
    }

    private List<Course> generateCourses() {
        return Arrays.asList(
                new Course(1, "Special Topics in Agronomy"),
                new Course(2, "Math"),
                new Course(3, "Biology")
        );
    }
}
