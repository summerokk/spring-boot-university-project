package com.att.university.controller;

import com.att.university.config.H2Config;
import com.att.university.config.WebTestConfig;
import com.att.university.dao.LessonDao;
import com.att.university.entity.AcademicRank;
import com.att.university.entity.Building;
import com.att.university.entity.Classroom;
import com.att.university.entity.Course;
import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import com.att.university.entity.Lesson;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Teacher;
import com.att.university.exception.ExceptionHandlerAdvice;
import com.att.university.exception.LessonControllerAdvice;
import com.att.university.exception.PersonHandleAdvice;
import com.att.university.exception.dao.PersonNotFoundException;
import com.att.university.exception.service.WrongEmailFormatException;
import com.att.university.exception.service.lesson.LessonSearchException;
import com.att.university.provider.lesson.LessonPdfFileProvider;
import com.att.university.service.LessonService;
import com.att.university.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {H2Config.class, WebTestConfig.class})
@WebAppConfiguration
class LessonControllerTest {
    private MockMvc mockMvc;

    @Mock
    private LessonService lessonService;

    @Mock
    private TeacherService teacherService;

    @Mock
    private LessonPdfFileProvider fileProvider;

    @InjectMocks
    private LessonController lessonController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(lessonController)
                .setControllerAdvice(new LessonControllerAdvice(), new ExceptionHandlerAdvice())
                .build();
    }

    @Test
    void getLessonsShouldReturn200Status() throws Exception {
        when(lessonService.findByDateBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Collections.singletonList(generateLesson()));
        when(teacherService.findAll()).thenReturn(Collections.singletonList(generateTeacher()));

        this.mockMvc.perform(get("/lessons/")).andExpect(status().isOk());

        verify(lessonService).findByDateBetween(any(LocalDate.class), any(LocalDate.class));
        verify(teacherService).findAll();
    }

    @Test
    void exportSchedulePdfShouldNotThrowException() throws Exception {
        when(lessonService.findByDateBetweenAndTeacherId(any(LocalDate.class), any(LocalDate.class), anyInt()))
                .thenReturn(Collections.singletonList(generateLesson()));

        doNothing().when(fileProvider).provideFile(any(OutputStream.class), anyList());

        this.mockMvc.perform(
                get("/lessons/pdf")
                        .param("startDate", LocalDate.now().toString())
                        .param("endDate", LocalDate.now().toString())
                        .param("teacherId", "1"))
                .andExpect(status().isOk());

        verify(lessonService).findByDateBetweenAndTeacherId(any(LocalDate.class), any(LocalDate.class), anyInt());
    }

    @Test
    void findLessonsShouldNotThrowException() throws Exception {
        List<LocalDate> weeks = Arrays.asList(
                LocalDate.now(),
                LocalDate.now()
        );

        when(teacherService.findById(anyInt())).thenReturn(generateTeacher());
        when(lessonService.findTeacherLessonWeeks(any(LocalDate.class), any(LocalDate.class), any())).thenReturn(weeks);
        when(lessonService.findTeacherWeekSchedule(anyInt(), anyList(), any()))
                .thenReturn(Collections.singletonList(generateLesson()));
        when(teacherService.findAll()).thenReturn(Collections.singletonList(generateTeacher()));


        this.mockMvc.perform(get("/lessons/find")
                .param("startDate", LocalDate.now().toString())
                .param("endDate", LocalDate.now().toString())
                .param("teacherId", "1"))
                .andExpect(status().isOk());

        verify(lessonService).findTeacherLessonWeeks(any(LocalDate.class), any(LocalDate.class), any());
    }

    @Test
    void findLessonsShouldThrowException() throws Exception {
        doThrow(PersonNotFoundException.class).when(teacherService).findById(anyInt());

        this.mockMvc.perform(get("/lessons/find")
                .param("startDate", LocalDate.now().toString())
                .param("endDate", LocalDate.now().toString())
                .param("teacherId", "1"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof LessonSearchException));
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
