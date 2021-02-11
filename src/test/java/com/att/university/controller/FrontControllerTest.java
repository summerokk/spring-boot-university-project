package com.att.university.controller;

import com.att.university.dao.AcademicRankDao;
import com.att.university.dao.BuildingDao;
import com.att.university.dao.ClassroomDao;
import com.att.university.dao.CourseDao;
import com.att.university.dao.GroupDao;
import com.att.university.dao.LessonDao;
import com.att.university.dao.ScienceDegreeDao;
import com.att.university.dao.StudentDao;
import com.att.university.dao.TeacherDao;
import com.att.university.entity.AcademicRank;
import com.att.university.entity.Building;
import com.att.university.entity.Classroom;
import com.att.university.entity.Course;
import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import com.att.university.entity.Lesson;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Student;
import com.att.university.entity.Teacher;
import com.att.university.view.ApplicationView;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    private LessonDao lessonDao;

    @Mock
    private CourseDao courseDao;

    @Mock
    private ClassroomDao classroomDao;

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
        when(view.readIntValue()).thenReturn(111, 0);
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

        Group group = new Group(1, "gf", faculty);
        Optional<Group> newGroup = Optional.of(group);

        when(view.readIntValue()).thenReturn(1, 1, 0);
        when(view.readStringValue()).thenReturn("name", "lastName", "email", "password");
        when(groupDao.findAll(anyInt(), anyInt())).thenReturn(groups);
        when(groupDao.findById(anyInt())).thenReturn(newGroup);

        frontController.runApplication();

        verify(studentDao).save(any(Student.class));
        verify(groupDao).findAll(anyInt(), anyInt());
        verify(view, times(3)).readIntValue();
        verify(view, times(4)).readStringValue();
        verify(view, times(9)).printMessage(anyString());
        verify(view, times(1)).printMessage(anyList());
    }

    @Test
    void addTeacherShouldReturnResult() {
        List<AcademicRank> academicRanks = Arrays.asList(
                new AcademicRank(1, "Assistant Professor"),
                new AcademicRank(2, "Full Professor"),
                new AcademicRank(3, "Endowed Professor")
        );
        Optional<AcademicRank> academicRank = Optional.of(new AcademicRank(1, "Assistant Professor"));

        List<ScienceDegree> scienceDegrees = Arrays.asList(
                new ScienceDegree(1, "Associate degree"),
                new ScienceDegree(2, "Doctoral degree"),
                new ScienceDegree(3, "Bachelor's degree"),
                new ScienceDegree(4, "Master's degree")
        );
        Optional<ScienceDegree> scienceDegree = Optional.of(new ScienceDegree(1, "Associate degree"));

        when(view.readIntValue()).thenReturn(2, 1, 2, 0);
        when(view.readStringValue()).thenReturn("name", "lastName", "email", "password", "link");
        when(academicRankDao.findAll(anyInt(), anyInt())).thenReturn(academicRanks);
        when(academicRankDao.findById(anyInt())).thenReturn(academicRank);
        when(scienceDegreeDao.findAll(anyInt(), anyInt())).thenReturn(scienceDegrees);
        when(scienceDegreeDao.findById(anyInt())).thenReturn(scienceDegree);

        frontController.runApplication();

        verify(teacherDao).save(any(Teacher.class));
        verify(academicRankDao).findAll(anyInt(), anyInt());
        verify(scienceDegreeDao).findAll(anyInt(), anyInt());
        verify(view, times(4)).readIntValue();
        verify(view, times(5)).readStringValue();
        verify(view, times(11)).printMessage(anyString());
        verify(view, times(2)).printMessage(anyList());
    }

    @Test
    void addLessonShouldReturnResult() {
        Optional<Course> course = Optional.of(new Course(1, "Special Topics in Agronomy"));

        Faculty faculty = new Faculty(1, "test");
        Optional<Group> group = Optional.of(new Group(1, "gf", faculty));

        AcademicRank academicRank = new AcademicRank(1, "Assistant Professor");
        ScienceDegree scienceDegree = new ScienceDegree(2, "Doctoral degree");

        Optional<Teacher> teacher = Optional.of(Teacher.builder()
                .withId(1)
                .withFirstName("Fedor")
                .withLastName("Tolov")
                .withEmail("tolof234@tmail.com")
                .withPassword("password")
                .withAcademicRank(academicRank)
                .withScienceDegree(scienceDegree)
                .withLinkedin("https://link.ru")
                .build());

        Optional<Classroom> classroom = Optional.of(
                new Classroom(1, 12, new Building(1, "Kirova 32"))
        );

        when(view.readIntValue()).thenReturn(3, 1, 1, 1, 1, 0);
        when(view.readStringValue()).thenReturn("2004-10-20T10:23");
        when(courseDao.findById(anyInt())).thenReturn(course);
        when(groupDao.findById(anyInt())).thenReturn(group);
        when(teacherDao.findById(anyInt())).thenReturn(teacher);
        when(classroomDao.findById(anyInt())).thenReturn(classroom);

        frontController.runApplication();

        verify(lessonDao, times(1)).save(any(Lesson.class));
        verify(courseDao, times(1)).findAll(anyInt(), anyInt());
        verify(groupDao, times(1)).findAll(anyInt(), anyInt());
        verify(teacherDao, times(1)).findAll(anyInt(), anyInt());
        verify(classroomDao, times(1)).findAll(anyInt(), anyInt());
        verify(classroomDao, times(1)).findById(anyInt());
        verify(teacherDao, times(1)).findById(anyInt());
        verify(groupDao, times(1)).findById(anyInt());
        verify(courseDao, times(1)).findById(anyInt());
        verify(view, times(6)).readIntValue();
        verify(view, times(9)).printMessage(anyString());
        verify(view, times(4)).printMessage(anyList());
    }

    @Test
    void showLessonsShouldReturnResult() {
        when(view.readIntValue()).thenReturn(5, 0);

        frontController.runApplication();
        verify(view, times(2)).readIntValue();
        verify(view, times(3)).printMessage(anyString());
        verify(view, times(1)).printMessage(anyList());
    }

    @Test
    void showLessonsShouldNotThrowException() {
        when(view.readIntValue()).thenReturn(4, 1, 0);

        frontController.runApplication();
        verify(view, times(3)).readIntValue();
        verify(view, times(5)).printMessage(anyString());
        verify(view, times(1)).printMessage(anyList());
    }
}
