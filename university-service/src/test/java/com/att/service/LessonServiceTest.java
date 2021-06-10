package com.att.service;

import com.att.dao.ClassroomRepository;
import com.att.dao.CourseRepository;
import com.att.dao.GroupRepository;
import com.att.dao.LessonRepository;
import com.att.dao.TeacherRepository;
import com.att.entity.AcademicRank;
import com.att.entity.Building;
import com.att.entity.Classroom;
import com.att.entity.Course;
import com.att.entity.Faculty;
import com.att.entity.Group;
import com.att.entity.Lesson;
import com.att.entity.ScienceDegree;
import com.att.entity.Teacher;
import com.att.exception.dao.LessonNotFoundException;
import com.att.request.lesson.LessonAddRequest;
import com.att.request.lesson.LessonUpdateRequest;
import com.att.service.impl.LessonServiceImpl;
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

    @InjectMocks
    private LessonServiceImpl lessonService;

    @Test
    void addShouldNotThrowExceptionIfRequestIsValid() {
        final LessonAddRequest lessonAddRequest = generateAddRequest();

        final Course course = generateCourse();
        final Teacher teacher = generateTeacher();
        final Classroom classroom = generateClassroom();
        final Group group = generateGroup();

        when(courseRepository.findById(lessonAddRequest.getCourseId())).thenReturn(Optional.of(course));
        when(teacherRepository.findById(anyInt())).thenReturn(Optional.of(teacher));
        when(groupRepository.findById(anyInt())).thenReturn(Optional.of(group));
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.of(classroom));

        lessonService.add(lessonAddRequest);

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

        when(courseRepository.findById(lessonAddRequest.getCourseId())).thenReturn(Optional.of(course));
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> lessonService.add(lessonAddRequest));

        verifyNoMoreInteractions(courseRepository, classroomRepository);
    }

    @Test
    void addShouldThrowExceptionIfCourseDoesNotExist() {
        final LessonAddRequest lessonAddRequest = generateAddRequest();

        when(courseRepository.findById(lessonAddRequest.getCourseId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> lessonService.add(lessonAddRequest));

        verifyNoMoreInteractions(courseRepository);
    }

    @Test
    void addShouldThrowExceptionIfGroupDoesNotExist() {
        final LessonAddRequest lessonAddRequest = generateAddRequest();
        final Course course = generateCourse();
        final Classroom classroom = generateClassroom();

        when(courseRepository.findById(lessonAddRequest.getCourseId())).thenReturn(Optional.of(course));
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.of(classroom));
        when(groupRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> lessonService.add(lessonAddRequest));

        verifyNoMoreInteractions(courseRepository, classroomRepository, groupRepository);
    }

    @Test
    void addShouldThrowExceptionIfTeacherDoesNotExist() {
        final LessonAddRequest lessonAddRequest = generateAddRequest();
        final Course course = generateCourse();
        final Classroom classroom = generateClassroom();
        final Group group = generateGroup();

        when(courseRepository.findById(lessonAddRequest.getCourseId())).thenReturn(Optional.of(course));
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.of(classroom));
        when(groupRepository.findById(anyInt())).thenReturn(Optional.of(group));
        when(teacherRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> lessonService.add(lessonAddRequest));

        verifyNoMoreInteractions(courseRepository, classroomRepository, groupRepository, teacherRepository);
    }

    @Test
    void updateShouldNotThrowExceptionIfRequestIsValid() {
        final LessonUpdateRequest lessonUpdateRequest = generateUpdateRequest();

        final Lesson lesson = generateLesson();
        final Course course = generateCourse();
        final Teacher teacher = generateTeacher();
        final Classroom classroom = generateClassroom();
        final Group group = generateGroup();

        when(lessonRepository.findById(anyInt())).thenReturn(Optional.of(lesson));
        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));
        when(teacherRepository.findById(anyInt())).thenReturn(Optional.of(teacher));
        when(groupRepository.findById(anyInt())).thenReturn(Optional.of(group));
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.of(classroom));

        lessonService.update(lessonUpdateRequest);

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


        when(lessonRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> lessonService.update(updateRequest));

        verifyNoMoreInteractions(lessonRepository);
    }

    @Test
    void updateShouldThrowExceptionIfClassroomDoesNotExist() {
        final LessonUpdateRequest updateRequest = generateUpdateRequest();

        final Lesson lesson = generateLesson();
        final Course course = generateCourse();

        when(lessonRepository.findById(anyInt())).thenReturn(Optional.of(lesson));
        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> lessonService.update(updateRequest));

        verifyNoMoreInteractions(courseRepository, classroomRepository, lessonRepository);
    }

    @Test
    void updateShouldThrowExceptionIfCourseDoesNotExist() {
        final LessonUpdateRequest updateRequest = generateUpdateRequest();

        final Lesson lesson = generateLesson();

        when(lessonRepository.findById(anyInt())).thenReturn(Optional.of(lesson));
        when(courseRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> lessonService.update(updateRequest));

        verifyNoMoreInteractions(courseRepository, lessonRepository);
    }

    @Test
    void updateShouldThrowExceptionIfTeacherDoesNotExist() {
        final LessonUpdateRequest updateRequest = generateUpdateRequest();

        final Lesson lesson = generateLesson();
        final Course course = generateCourse();
        final Classroom classroom = generateClassroom();
        final Group group = generateGroup();

        when(lessonRepository.findById(anyInt())).thenReturn(Optional.of(lesson));
        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));
        when(groupRepository.findById(anyInt())).thenReturn(Optional.of(group));
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.of(classroom));
        when(teacherRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> lessonService.update(updateRequest));

        verify(courseRepository).findById(anyInt());
        verify(lessonRepository).findById(anyInt());
        verify(groupRepository).findById(anyInt());
        verify(teacherRepository).findById(anyInt());
        verify(classroomRepository).findById(anyInt());
        verifyNoMoreInteractions(courseRepository, lessonRepository, groupRepository, teacherRepository, classroomRepository);
    }

    @Test
    void updateShouldThrowExceptionIfGroupDoesNotExist() {
        final LessonUpdateRequest updateRequest = generateUpdateRequest();

        final Lesson lesson = generateLesson();
        final Course course = generateCourse();
        final Classroom classroom = generateClassroom();

        when(lessonRepository.findById(anyInt())).thenReturn(Optional.of(lesson));
        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.of(classroom));
        when(groupRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> lessonService.update(updateRequest));

        verify(courseRepository).findById(anyInt());
        verify(lessonRepository).findById(anyInt());
        verify(groupRepository).findById(anyInt());
        verify(classroomRepository).findById(anyInt());
        verifyNoMoreInteractions(courseRepository, lessonRepository, groupRepository, teacherRepository, classroomRepository);
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
