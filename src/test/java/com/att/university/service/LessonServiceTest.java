package com.att.university.service;

import com.att.university.dao.ClassroomRepository;
import com.att.university.dao.CourseRepository;
import com.att.university.dao.GroupRepository;
import com.att.university.dao.LessonRepository;
import com.att.university.dao.TeacherRepository;
import com.att.university.entity.AcademicRank;
import com.att.university.entity.Building;
import com.att.university.entity.Classroom;
import com.att.university.entity.Course;
import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import com.att.university.entity.Lesson;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Teacher;
import com.att.university.exception.dao.LessonNotFoundException;
import com.att.university.request.lesson.LessonAddRequest;
import com.att.university.request.lesson.LessonUpdateRequest;
import com.att.university.service.impl.LessonServiceImpl;
import com.att.university.validator.lesson.LessonAddValidator;
import com.att.university.validator.lesson.LessonUpdateValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private ClassroomRepository classroomRepository;

    @Mock
    private LessonUpdateValidator lessonUpdateValidator;

    @Mock
    private LessonAddValidator lessonAddValidator;

    @InjectMocks
    private LessonServiceImpl lessonService;

    @Test
    void lessonAddValidatorShouldThrowException() {
        final LessonAddRequest lessonAddRequest = generateAddRequest();

        doThrow(RuntimeException.class).when(lessonAddValidator).validate(lessonAddRequest);

        assertThrows(RuntimeException.class, () -> lessonService.add(lessonAddRequest));
        verify(lessonAddValidator).validate(any(LessonAddRequest.class));
    }

    @Test
    void lessonUpdateValidatorShouldThrowException() {
        final LessonUpdateRequest lessonUpdateRequest = generateUpdateRequest();

        doThrow(RuntimeException.class).when(lessonUpdateValidator).validate(lessonUpdateRequest);

        assertThrows(RuntimeException.class, () -> lessonService.update(lessonUpdateRequest));
        verify(lessonUpdateValidator).validate(any(LessonUpdateRequest.class));
    }

    @Test
    void addShouldNotThrowExceptionIfRequestIsValid() {
        final LessonAddRequest lessonAddRequest = generateAddRequest();

        final Course course = generateCourse();
        final Teacher teacher = generateTeacher();
        final Classroom classroom = generateClassroom();
        final Group group = generateGroup();

        doNothing().when(lessonAddValidator).validate(any(LessonAddRequest.class));
        when(courseRepository.findById(lessonAddRequest.getCourseId())).thenReturn(Optional.of(course));
        when(teacherRepository.findById(anyInt())).thenReturn(Optional.of(teacher));
        when(groupRepository.findById(anyInt())).thenReturn(Optional.of(group));
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.of(classroom));

        lessonService.add(lessonAddRequest);

        verify(lessonAddValidator).validate(any(LessonAddRequest.class));
        verify(courseRepository).findById(anyInt());
        verify(groupRepository).findById(anyInt());
        verify(teacherRepository).findById(anyInt());
        verify(classroomRepository).findById(anyInt());
        verify(lessonRepository).save(any(Lesson.class));
    }

    @Test
    void addShouldThrowExceptionIfClassroomDoesNotExist() {
        final LessonAddRequest lessonAddRequest = generateAddRequest();
        final Course course = generateCourse();

        doNothing().when(lessonAddValidator).validate(any(LessonAddRequest.class));
        when(courseRepository.findById(lessonAddRequest.getCourseId())).thenReturn(Optional.of(course));
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> lessonService.add(lessonAddRequest));

        verify(lessonAddValidator).validate(any(LessonAddRequest.class));
        verifyNoMoreInteractions(courseRepository, classroomRepository, lessonAddValidator);
    }

    @Test
    void addShouldThrowExceptionIfCourseDoesNotExist() {
        final LessonAddRequest lessonAddRequest = generateAddRequest();

        doNothing().when(lessonAddValidator).validate(any(LessonAddRequest.class));
        when(courseRepository.findById(lessonAddRequest.getCourseId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> lessonService.add(lessonAddRequest));

        verify(lessonAddValidator).validate(any(LessonAddRequest.class));
        verifyNoMoreInteractions(courseRepository, lessonAddValidator);
    }

    @Test
    void addShouldThrowExceptionIfGroupDoesNotExist() {
        final LessonAddRequest lessonAddRequest = generateAddRequest();
        final Course course = generateCourse();
        final Classroom classroom = generateClassroom();

        doNothing().when(lessonAddValidator).validate(any(LessonAddRequest.class));
        when(courseRepository.findById(lessonAddRequest.getCourseId())).thenReturn(Optional.of(course));
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.of(classroom));
        when(groupRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> lessonService.add(lessonAddRequest));

        verify(lessonAddValidator).validate(any(LessonAddRequest.class));
        verifyNoMoreInteractions(courseRepository, classroomRepository, groupRepository, lessonAddValidator);
    }

    @Test
    void addShouldThrowExceptionIfTeacherDoesNotExist() {
        final LessonAddRequest lessonAddRequest = generateAddRequest();
        final Course course = generateCourse();
        final Classroom classroom = generateClassroom();
        final Group group = generateGroup();

        doNothing().when(lessonAddValidator).validate(any(LessonAddRequest.class));
        when(courseRepository.findById(lessonAddRequest.getCourseId())).thenReturn(Optional.of(course));
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.of(classroom));
        when(groupRepository.findById(anyInt())).thenReturn(Optional.of(group));
        when(teacherRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> lessonService.add(lessonAddRequest));

        verify(lessonAddValidator).validate(any(LessonAddRequest.class));
        verifyNoMoreInteractions(courseRepository, classroomRepository, groupRepository, teacherRepository, lessonAddValidator);
    }

    @Test
    void updateShouldNotThrowExceptionIfRequestIsValid() {
        final LessonUpdateRequest lessonUpdateRequest = generateUpdateRequest();

        final Lesson lesson = generateLesson();
        final Course course = generateCourse();
        final Teacher teacher = generateTeacher();
        final Classroom classroom = generateClassroom();
        final Group group = generateGroup();

        doNothing().when(lessonUpdateValidator).validate(any(LessonUpdateRequest.class));
        when(lessonRepository.findById(anyInt())).thenReturn(Optional.of(lesson));
        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));
        when(teacherRepository.findById(anyInt())).thenReturn(Optional.of(teacher));
        when(groupRepository.findById(anyInt())).thenReturn(Optional.of(group));
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.of(classroom));

        lessonService.update(lessonUpdateRequest);

        verify(lessonUpdateValidator).validate(any(LessonUpdateRequest.class));
        verify(courseRepository).findById(anyInt());
        verify(lessonRepository).findById(anyInt());
        verify(groupRepository).findById(anyInt());
        verify(teacherRepository).findById(anyInt());
        verify(classroomRepository).findById(anyInt());
        verify(lessonRepository).save(any(Lesson.class));
    }

    @Test
    void updateShouldThrowExceptionIfLessonDoesNotExist() {
        final LessonUpdateRequest updateRequest = generateUpdateRequest();


        doNothing().when(lessonUpdateValidator).validate(any(LessonUpdateRequest.class));
        when(lessonRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> lessonService.update(updateRequest));

        verify(lessonUpdateValidator).validate(any(LessonUpdateRequest.class));
        verifyNoMoreInteractions(lessonRepository, lessonUpdateValidator);
    }

    @Test
    void updateShouldThrowExceptionIfClassroomDoesNotExist() {
        final LessonUpdateRequest updateRequest = generateUpdateRequest();

        final Lesson lesson = generateLesson();
        final Course course = generateCourse();

        doNothing().when(lessonUpdateValidator).validate(any(LessonUpdateRequest.class));
        when(lessonRepository.findById(anyInt())).thenReturn(Optional.of(lesson));
        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> lessonService.update(updateRequest));

        verify(lessonUpdateValidator).validate(any(LessonUpdateRequest.class));
        verifyNoMoreInteractions(courseRepository, classroomRepository, lessonRepository, lessonAddValidator);
    }

    @Test
    void updateShouldThrowExceptionIfCourseDoesNotExist() {
        final LessonUpdateRequest updateRequest = generateUpdateRequest();

        final Lesson lesson = generateLesson();

        doNothing().when(lessonUpdateValidator).validate(any(LessonUpdateRequest.class));
        when(lessonRepository.findById(anyInt())).thenReturn(Optional.of(lesson));
        when(courseRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> lessonService.update(updateRequest));

        verify(lessonUpdateValidator).validate(any(LessonUpdateRequest.class));
        verifyNoMoreInteractions(courseRepository, lessonRepository, lessonAddValidator);
    }

    @Test
    void updateShouldThrowExceptionIfTeacherDoesNotExist() {
        final LessonUpdateRequest updateRequest = generateUpdateRequest();

        final Lesson lesson = generateLesson();
        final Course course = generateCourse();
        final Classroom classroom = generateClassroom();
        final Group group = generateGroup();

        doNothing().when(lessonUpdateValidator).validate(any(LessonUpdateRequest.class));
        when(lessonRepository.findById(anyInt())).thenReturn(Optional.of(lesson));
        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));
        when(groupRepository.findById(anyInt())).thenReturn(Optional.of(group));
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.of(classroom));
        when(teacherRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> lessonService.update(updateRequest));

        verify(lessonUpdateValidator).validate(any(LessonUpdateRequest.class));
        verify(courseRepository).findById(anyInt());
        verify(lessonRepository).findById(anyInt());
        verify(groupRepository).findById(anyInt());
        verify(teacherRepository).findById(anyInt());
        verify(classroomRepository).findById(anyInt());
        verifyNoMoreInteractions(courseRepository, lessonRepository, groupRepository, lessonUpdateValidator, teacherRepository, classroomRepository);
    }

    @Test
    void updateShouldThrowExceptionIfGroupDoesNotExist() {
        final LessonUpdateRequest updateRequest = generateUpdateRequest();

        final Lesson lesson = generateLesson();
        final Course course = generateCourse();
        final Classroom classroom = generateClassroom();

        doNothing().when(lessonUpdateValidator).validate(any(LessonUpdateRequest.class));
        when(lessonRepository.findById(anyInt())).thenReturn(Optional.of(lesson));
        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.of(classroom));
        when(groupRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> lessonService.update(updateRequest));

        verify(lessonUpdateValidator).validate(any(LessonUpdateRequest.class));
        verify(courseRepository).findById(anyInt());
        verify(lessonRepository).findById(anyInt());
        verify(groupRepository).findById(anyInt());
        verify(classroomRepository).findById(anyInt());
        verifyNoMoreInteractions(courseRepository, lessonRepository, groupRepository, lessonUpdateValidator, teacherRepository, classroomRepository);
    }

    @Test
    void findTeacherLessonWeeksShouldNotThrowException() {
        List<Timestamp> weeks = Arrays.asList(
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now())
        );

        LocalDateTime startDate = LocalDateTime.parse("2020-10-12T10:23");
        LocalDateTime endDate = LocalDateTime.parse("2020-10-15T10:23");

        when(lessonRepository.findTeacherLessonWeeks(eq(startDate), eq(endDate), anyInt()))
                .thenReturn(weeks);

        assertDoesNotThrow(() -> lessonService.findTeacherLessonWeeks(startDate, endDate, 1));

        verify(lessonRepository).findTeacherLessonWeeks(any(LocalDateTime.class), any(LocalDateTime.class), anyInt());
    }

    @Test
    void findTeacherWeekScheduleShouldNotThrowException() {
        List<LocalDateTime> weeks = spy(new ArrayList<>());
        weeks.add(LocalDateTime.now());

        when(weeks.get(anyInt())).thenReturn(LocalDateTime.now());

        assertDoesNotThrow(() -> lessonService.findTeacherWeekSchedule(1, weeks, 1));

        verify(lessonRepository).findByDateBetweenAndTeacherId(any(LocalDateTime.class), any(LocalDateTime.class), eq(1));
    }

    @Test
    void findByDateBetweenAndTeacherIdShouldNotThrowException() {
        List<Lesson> lessons = Collections.singletonList(generateLesson());
        LocalDateTime startDate = LocalDateTime.parse("2020-10-12T10:23");
        LocalDateTime endDate = LocalDateTime.parse("2020-10-15T10:23");

        when(lessonRepository.findByDateBetweenAndTeacherId(any(LocalDateTime.class), any(LocalDateTime.class), anyInt()))
                .thenReturn(lessons);

        assertDoesNotThrow(() -> lessonService.findByDateBetweenAndTeacherId(startDate, endDate, 1));

        verify(lessonRepository).findByDateBetweenAndTeacherId(any(LocalDateTime.class), any(LocalDateTime.class), eq(1));
    }

    @Test
    void findByDateBetweenShouldNotThrowException() {
        List<Lesson> lessons = Collections.singletonList(generateLesson());
        LocalDateTime startDate = LocalDateTime.parse("2020-10-12T10:23");
        LocalDateTime endDate = LocalDateTime.parse("2020-10-15T10:23");

        when(lessonRepository.findByDateBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(lessons);

        assertDoesNotThrow(() -> lessonService.findByDateBetween(startDate, endDate));

        verify(lessonRepository).findByDateBetween(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void findByIdShouldThrowExceptionWhenLessonDoesNotExist() {
        Integer id = 4;

        when(lessonRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> lessonService.findById(id));

        verify(lessonRepository).findById(anyInt());
        verifyNoMoreInteractions(lessonRepository);
    }

    @Test
    void findByIdShouldReturnLessonWhenStudentExists() {
        Integer id = 4;

        when(lessonRepository.findById(anyInt())).thenReturn(Optional.of(generateLesson()));

        lessonService.findById(id);

        verify(lessonRepository).findById(anyInt());
        verifyNoMoreInteractions(lessonRepository);
    }

    @Test
    void deleteLessonShouldThrowNotFoundExceptionIfLessonNotFound() {
        when(lessonRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> lessonService.deleteById(1));

        verify(lessonRepository).findById(anyInt());
        verifyNoMoreInteractions(lessonRepository);
    }

    @Test
    void deleteStudentShouldNotThrowExceptionIfStudentIsFound() {
        when(lessonRepository.findById(anyInt())).thenReturn(Optional.of(generateLesson()));

        lessonService.deleteById(1);

        verify(lessonRepository).findById(anyInt());
        verify(lessonRepository).deleteById(anyInt());
        verifyNoMoreInteractions(lessonRepository);
    }

    private LessonAddRequest generateAddRequest() {
        return LessonAddRequest.builder()
                .withTeacherId(1)
                .withClassroomId(1)
                .withDate(LocalDateTime.parse("2004-10-20T10:23"))
                .withCourseId(1)
                .withGroupId(1)
                .build();
    }

    private LessonUpdateRequest generateUpdateRequest() {
        return LessonUpdateRequest.builder()
                .withId(1)
                .withTeacherId(1)
                .withClassroomId(1)
                .withDate(LocalDateTime.parse("2004-10-20T10:23"))
                .withCourseId(1)
                .withGroupId(1)
                .build();
    }

    private Lesson generateLesson() {
        return Lesson.builder()
                .withId(1)
                .withClassroom(generateClassroom())
                .withGroup(generateGroup())
                .withCourse(generateCourse())
                .withTeacher(generateTeacher())
                .build();
    }

    private Teacher generateTeacher() {
        AcademicRank academicRank = new AcademicRank(1, "testRank");
        ScienceDegree scienceDegree = new ScienceDegree(1, "testDegree");

        return Teacher.builder()
                .withId(1)
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test@test.ru")
                .withPassword("1234567890")
                .withLinkedin("http://test.ru")
                .withAcademicRank(academicRank)
                .withScienceDegree(scienceDegree)
                .build();
    }

    private Classroom generateClassroom() {
        Building building = new Building(1, "Address");

        return new Classroom(1, 14, building);
    }

    private Group generateGroup() {
        Faculty faculty = new Faculty(1, "test");

        return new Group(1, "test-23", faculty);
    }

    private Course generateCourse() {
        return new Course(1, "testCourse");
    }
}
