package com.att.university.service;

import com.att.university.dao.AcademicRankDao;
import com.att.university.dao.ClassroomDao;
import com.att.university.dao.CourseDao;
import com.att.university.dao.GroupDao;
import com.att.university.dao.LessonDao;
import com.att.university.dao.ScienceDegreeDao;
import com.att.university.dao.TeacherDao;
import com.att.university.entity.AcademicRank;
import com.att.university.entity.Building;
import com.att.university.entity.Classroom;
import com.att.university.entity.Course;
import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import com.att.university.entity.Lesson;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Teacher;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {
    @Mock
    private CourseDao courseDao;

    @Mock
    private TeacherDao teacherDao;

    @Mock
    private GroupDao groupDao;

    @Mock
    private LessonDao lessonDao;

    @Mock
    private ClassroomDao classroomDao;
    
    @Mock
    private LessonUpdateValidator lessonUpdateValidator;

    @Mock
    private LessonAddValidator lessonAddValidator;

    @InjectMocks
    private LessonServiceImpl lessonService;

    @Test
    void addShouldNotThrowExceptionIfRequestIsValid() {
        final LessonAddRequest lessonAddRequest = generateAddRequest();

        final Course course = generateCourse();
        final Teacher teacher = generateTeacher();
        final Classroom classroom = generateClassroom();
        final Group group = generateGroup();

        doNothing().when(lessonAddValidator).validate(any(LessonAddRequest.class));
        when(courseDao.findById(lessonAddRequest.getCourseId())).thenReturn(Optional.of(course));
        when(teacherDao.findById(anyInt())).thenReturn(Optional.of(teacher));
        when(groupDao.findById(anyInt())).thenReturn(Optional.of(group));
        when(classroomDao.findById(anyInt())).thenReturn(Optional.of(classroom));

        lessonService.add(lessonAddRequest);

        verify(lessonAddValidator).validate(any(LessonAddRequest.class));
        verify(courseDao).findById(anyInt());
        verify(groupDao).findById(anyInt());
        verify(teacherDao).findById(anyInt());
        verify(classroomDao).findById(anyInt());
        verify(lessonDao).save(any(Lesson.class));
    }

    @Test
    void addShouldThrowExceptionIfClassroomDoesNotExist() {
        final LessonAddRequest lessonAddRequest = generateAddRequest();
        final Course course = generateCourse();

        doNothing().when(lessonAddValidator).validate(any(LessonAddRequest.class));
        when(courseDao.findById(lessonAddRequest.getCourseId())).thenReturn(Optional.of(course));
        when(classroomDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> lessonService.add(lessonAddRequest));

        verify(lessonAddValidator).validate(any(LessonAddRequest.class));
        verifyNoMoreInteractions(courseDao, classroomDao, lessonAddValidator);
    }

    @Test
    void addShouldThrowExceptionIfCourseDoesNotExist() {
        final LessonAddRequest lessonAddRequest = generateAddRequest();

        doNothing().when(lessonAddValidator).validate(any(LessonAddRequest.class));
        when(courseDao.findById(lessonAddRequest.getCourseId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> lessonService.add(lessonAddRequest));

        verify(lessonAddValidator).validate(any(LessonAddRequest.class));
        verifyNoMoreInteractions(courseDao, lessonAddValidator);
    }

    @Test
    void addShouldThrowExceptionIfGroupDoesNotExist() {
        final LessonAddRequest lessonAddRequest = generateAddRequest();
        final Course course = generateCourse();
        final Classroom classroom = generateClassroom();

        doNothing().when(lessonAddValidator).validate(any(LessonAddRequest.class));
        when(courseDao.findById(lessonAddRequest.getCourseId())).thenReturn(Optional.of(course));
        when(classroomDao.findById(anyInt())).thenReturn(Optional.of(classroom));
        when(groupDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> lessonService.add(lessonAddRequest));

        verify(lessonAddValidator).validate(any(LessonAddRequest.class));
        verifyNoMoreInteractions(courseDao, classroomDao, groupDao, lessonAddValidator);
    }

    @Test
    void addShouldThrowExceptionIfTeacherDoesNotExist() {
        final LessonAddRequest lessonAddRequest = generateAddRequest();
        final Course course = generateCourse();
        final Classroom classroom = generateClassroom();
        final Group group = generateGroup();

        doNothing().when(lessonAddValidator).validate(any(LessonAddRequest.class));
        when(courseDao.findById(lessonAddRequest.getCourseId())).thenReturn(Optional.of(course));
        when(classroomDao.findById(anyInt())).thenReturn(Optional.of(classroom));
        when(groupDao.findById(anyInt())).thenReturn(Optional.of(group));
        when(teacherDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> lessonService.add(lessonAddRequest));

        verify(lessonAddValidator).validate(any(LessonAddRequest.class));
        verifyNoMoreInteractions(courseDao, classroomDao, groupDao, teacherDao, lessonAddValidator);
    }

//    @Test
//    void registerShouldThrowExceptionIfEmailExists() {
//        final TeacherRegisterRequest registerRequest = generateRegisterRequest();
//        final Teacher teacher = generateTeacher();
//
//        doNothing().when(teacherRegisterValidator).validate(any(TeacherRegisterRequest.class));
//        when(teacherDao.findByEmail(teacher.getEmail())).thenReturn(Optional.of(teacher));
//
//        assertThrows(RuntimeException.class, () -> lessonService.register(registerRequest));
//
//        verify(teacherRegisterValidator).validate(any(TeacherRegisterRequest.class));
//        verify(teacherDao).findByEmail(anyString());
//        verifyNoMoreInteractions(teacherDao, teacherRegisterValidator);
//    }
//
//    @Test
//    void registerShouldThrowExceptionIfAcademicRankDoesNotExist() {
//        final TeacherRegisterRequest registerRequest = generateRegisterRequest();
//
//        doNothing().when(teacherRegisterValidator).validate(any(TeacherRegisterRequest.class));
//        when(teacherDao.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
//        when(academicRankDao.findById(anyInt())).thenReturn(Optional.empty());
//
//        assertThrows(RuntimeException.class, () -> lessonService.register(registerRequest));
//
//        verify(teacherRegisterValidator).validate(any(TeacherRegisterRequest.class));
//        verify(teacherDao).findByEmail(anyString());
//        verify(academicRankDao).findById(anyInt());
//        verifyNoMoreInteractions(teacherDao, teacherRegisterValidator, academicRankDao, scienceDegreeDao);
//    }
//
//    @Test
//    void registerShouldThrowExceptionIfScienceDegreeDoesNotExist() {
//        final TeacherRegisterRequest registerRequest = generateRegisterRequest();
//
//        doNothing().when(teacherRegisterValidator).validate(any(TeacherRegisterRequest.class));
//        when(teacherDao.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
//        when(academicRankDao.findById(anyInt())).thenReturn(Optional.of(generateRank()));
//        when(scienceDegreeDao.findById(anyInt())).thenReturn(Optional.empty());
//
//        assertThrows(RuntimeException.class, () -> lessonService.register(registerRequest));
//
//        verify(teacherRegisterValidator).validate(any(TeacherRegisterRequest.class));
//        verify(teacherDao).findByEmail(anyString());
//        verify(academicRankDao).findById(anyInt());
//        verify(scienceDegreeDao).findById(anyInt());
//        verifyNoMoreInteractions(teacherDao, teacherRegisterValidator, academicRankDao, scienceDegreeDao);
//    }
//
//    @Test
//    void updateShouldNotThrowExceptionWhenUserExist() {
//        final TeacherUpdateRequest teacherUpdateRequest = generateUpdateRequest();
//        final Teacher teacher = generateTeacher();
//
//        doNothing().when(teacherUpdateValidator).validate(any(TeacherUpdateRequest.class));
//        when(teacherDao.findById(teacher.getId())).thenReturn(Optional.of(teacher));
//        when(scienceDegreeDao.findById(anyInt())).thenReturn(Optional.of(generateDegree()));
//        when(academicRankDao.findById(anyInt())).thenReturn(Optional.of(generateRank()));
//
//        lessonService.update(teacherUpdateRequest);
//
//        verify(teacherUpdateValidator).validate(any(TeacherUpdateRequest.class));
//        verify(teacherDao).findById(anyInt());
//        verify(scienceDegreeDao).findById(anyInt());
//        verify(academicRankDao).findById(anyInt());
//        verify(teacherDao).update(any(Teacher.class));
//    }
//
//    @Test
//    void updateShouldThrowExceptionIfScienceDegreeDoesNotExist() {
//        final TeacherUpdateRequest teacherUpdateRequest = generateUpdateRequest();
//        final Teacher teacher = generateTeacher();
//
//        doNothing().when(teacherUpdateValidator).validate(any(TeacherUpdateRequest.class));
//        when(teacherDao.findById(teacherUpdateRequest.getId())).thenReturn(Optional.of(teacher));
//        when(academicRankDao.findById(anyInt())).thenReturn(Optional.of(generateRank()));
//        when(scienceDegreeDao.findById(anyInt())).thenReturn(Optional.empty());
//
//        assertThrows(RuntimeException.class, () -> lessonService.update(teacherUpdateRequest));
//
//        verify(teacherUpdateValidator).validate(any(TeacherUpdateRequest.class));
//        verify(teacherDao).findById(anyInt());
//        verify(academicRankDao).findById(anyInt());
//        verify(scienceDegreeDao).findById(anyInt());
//        verifyNoMoreInteractions(teacherDao, teacherUpdateValidator, academicRankDao, scienceDegreeDao);
//    }
//
//    @Test
//    void updateShouldThrowExceptionIfAcademicRankDoesNotExist() {
//        final TeacherUpdateRequest teacherUpdateRequest = generateUpdateRequest();
//        final Teacher teacher = generateTeacher();
//
//        doNothing().when(teacherUpdateValidator).validate(any(TeacherUpdateRequest.class));
//        when(teacherDao.findById(teacherUpdateRequest.getId())).thenReturn(Optional.of(teacher));
//        when(academicRankDao.findById(anyInt())).thenReturn(Optional.empty());
//
//        assertThrows(RuntimeException.class, () -> lessonService.update(teacherUpdateRequest));
//
//        verify(teacherUpdateValidator).validate(any(TeacherUpdateRequest.class));
//        verify(teacherDao).findById(anyInt());
//        verify(academicRankDao).findById(anyInt());
//        verifyNoMoreInteractions(teacherDao, teacherUpdateValidator, academicRankDao, scienceDegreeDao);
//    }
//
//    @Test
//    void updateShouldThrowExceptionIfUserExists() {
//        final TeacherUpdateRequest teacherUpdateRequest = generateUpdateRequest();
//
//        doNothing().when(teacherUpdateValidator).validate(any(TeacherUpdateRequest.class));
//        when(teacherDao.findById(teacherUpdateRequest.getId())).thenReturn(Optional.empty());
//
//        assertThrows(RuntimeException.class, () -> lessonService.update(teacherUpdateRequest));
//
//        verify(teacherUpdateValidator).validate(any(TeacherUpdateRequest.class));
//        verify(teacherDao).findById(anyInt());
//        verifyNoMoreInteractions(teacherDao, teacherUpdateValidator);
//    }

    private LessonAddRequest generateAddRequest() {
        return LessonAddRequest.builder()
                .withTeacherId(1)
                .withClassroomId(1)
                .withDate("2004-10-20T10:23")
                .withCourseId(1)
                .withGroupId(1)
                .build();
    }

    private LessonUpdateRequest generateUpdateRequest() {
        return LessonUpdateRequest.builder()
                .withId(1)
                .withTeacherId(1)
                .withClassroomId(1)
                .withDate("2004-10-20T10:23")
                .withCourseId(1)
                .withGroupId(1)
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
