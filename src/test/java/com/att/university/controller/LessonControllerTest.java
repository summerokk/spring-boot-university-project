package com.att.university.controller;

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
import com.att.university.exception.dao.PersonNotFoundException;
import com.att.university.exception.service.lesson.LessonSearchException;
import com.att.university.provider.lesson.LessonPdfFileProvider;
import com.att.university.request.lesson.LessonAddRequest;
import com.att.university.request.lesson.LessonUpdateRequest;
import com.att.university.service.ClassroomService;
import com.att.university.service.CourseService;
import com.att.university.service.GroupService;
import com.att.university.service.LessonService;
import com.att.university.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class LessonControllerTest {
    private MockMvc mockMvc;

    @Mock
    private LessonService lessonService;

    @Mock
    private TeacherService teacherService;

    @Mock
    private GroupService groupService;

    @Mock
    private ClassroomService classroomService;

    @Mock
    private CourseService courseService;

    @Mock
    private LessonPdfFileProvider fileProvider;

    @InjectMocks
    private LessonController lessonController;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(lessonController)
                .setControllerAdvice(new LessonControllerAdvice(), new ExceptionHandlerAdvice())
                .build();
    }

    @Test
    void getLessonsShouldReturn200Status() throws Exception {
        when(lessonService.findByDateBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Collections.singletonList(generateLesson()));
        when(teacherService.findAllWithoutPagination()).thenReturn(Collections.singletonList(generateTeacher()));

        this.mockMvc.perform(get("/lessons/")).andExpect(status().isOk());

        verify(lessonService).findByDateBetween(any(LocalDate.class), any(LocalDate.class));
        verify(teacherService).findAllWithoutPagination();
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
        when(teacherService.findAllWithoutPagination()).thenReturn(Collections.singletonList(generateTeacher()));


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

    @Test
    void performGetShowLessonShouldReturn200StatusWithAttributes() throws Exception {
        when(lessonService.findById(anyInt())).thenReturn(generateLesson());
        when(teacherService.findAllWithoutPagination()).thenReturn(Collections.singletonList(generateTeacher()));
        when(groupService.findAll()).thenReturn(Collections.singletonList(generateGroup()));
        when(courseService.findAll()).thenReturn(Collections.singletonList(generateCourse()));
        when(classroomService.findAll()).thenReturn(Collections.singletonList(generateClassroom()));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/lessons/show/{id}", 1);

        this.mockMvc
                .perform(request)
                .andExpect(model().attributeExists("groups", "courses", "teachers", "classrooms", "lesson"))
                .andExpect(status().isOk())
                .andExpect(view().name("lessons/show"));
    }

    @Test
    void performGetCreateLessonShouldReturnOkStatus() throws Exception {
        when(teacherService.findAllWithoutPagination()).thenReturn(Collections.singletonList(generateTeacher()));
        when(groupService.findAll()).thenReturn(Collections.singletonList(generateGroup()));
        when(courseService.findAll()).thenReturn(Collections.singletonList(generateCourse()));
        when(classroomService.findAll()).thenReturn(Collections.singletonList(generateClassroom()));


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/lessons/create");

        this.mockMvc.perform(request)
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("groups", "courses", "teachers", "classrooms", "addRequest"))
                .andExpect(view().name("lessons/add"));

        verify(classroomService).findAll();
        verify(teacherService).findAllWithoutPagination();
        verify(groupService).findAll();
        verify(classroomService).findAll();
    }

    @Test
    void performPostStoreLessonShouldReturn302Status() throws Exception {
        final LessonAddRequest addRequest = generateAddRequest();

        doNothing().when(lessonService).add(addRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/lessons/store")
                .flashAttr("lessonAddRequest", addRequest);

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(flash().attributeExists("successCreate"));

        verify(lessonService).add(addRequest);
    }

    @Test
    void performPostUpdateLessonShouldReturn302Status() throws Exception {
        final LessonUpdateRequest updateRequest = generateUpdateRequest();

        doNothing().when(lessonService).update(updateRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/lessons/update")
                .flashAttr("lessonUpdateRequest", updateRequest);

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/lessons/show/1"));

        verify(lessonService).update(updateRequest);
    }

    @Test
    void performDeleteLessonShouldReturn302Status() throws Exception {
        doNothing().when(lessonService).deleteById(anyInt());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/lessons/delete")
                .param("id", "1");

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/lessons/"));

        verify(lessonService).deleteById(anyInt());
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
}
