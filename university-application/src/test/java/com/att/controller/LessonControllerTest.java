package com.att.controller;

import com.att.exception.dao.PersonNotFoundException;
import com.att.exception.service.lesson.LessonSearchException;
import com.att.provider.lesson.LessonPdfFileProvider;
import com.att.request.lesson.LessonAddRequest;
import com.att.request.lesson.LessonUpdateRequest;
import com.att.entity.AcademicRank;
import com.att.entity.Building;
import com.att.entity.Classroom;
import com.att.entity.Course;
import com.att.entity.Faculty;
import com.att.entity.Group;
import com.att.entity.Lesson;
import com.att.entity.ScienceDegree;
import com.att.entity.Teacher;
import com.att.service.ClassroomService;
import com.att.service.CourseService;
import com.att.service.GroupService;
import com.att.service.LessonService;
import com.att.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.OutputStream;
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

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = LessonController.class)
@AutoConfigureMockMvc(addFilters = false)
class LessonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonService lessonService;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private GroupService groupService;

    @MockBean
    private ClassroomService classroomService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private LessonPdfFileProvider fileProvider;
    
    @Test
    void getLessonsShouldReturn200Status() throws Exception {
        when(lessonService.findByDateBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.singletonList(generateLesson()));
        when(teacherService.findAll()).thenReturn(Collections.singletonList(generateTeacher()));

        this.mockMvc.perform(get("/lessons/")).andExpect(status().isOk());

        verify(lessonService).findByDateBetween(any(LocalDateTime.class), any(LocalDateTime.class));
        verify(teacherService).findAll();
    }

    @Test
    void exportSchedulePdfShouldNotThrowException() throws Exception {
        when(lessonService.findByDateBetweenAndTeacherId(any(LocalDateTime.class), any(LocalDateTime.class), anyInt()))
                .thenReturn(Collections.singletonList(generateLesson()));

        doNothing().when(fileProvider).provideFile(any(OutputStream.class), anyList());

        this.mockMvc.perform(
                get("/lessons/pdf")
                        .param("startDate", LocalDateTime.now().toString())
                        .param("endDate", LocalDateTime.now().toString())
                        .param("teacherId", "1"))
                .andExpect(status().isOk());

        verify(lessonService).findByDateBetweenAndTeacherId(any(LocalDateTime.class), any(LocalDateTime.class), anyInt());
    }

    @Test
    void findLessonsShouldNotThrowException() throws Exception {
        List<LocalDateTime> weeks = Arrays.asList(
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(teacherService.findById(anyInt())).thenReturn(generateTeacher());
        when(lessonService.findTeacherLessonWeeks(any(LocalDateTime.class), any(LocalDateTime.class), any())).thenReturn(weeks);
        when(lessonService.findTeacherWeekSchedule(anyInt(), anyList(), any()))
                .thenReturn(Collections.singletonList(generateLesson()));
        when(teacherService.findAll()).thenReturn(Collections.singletonList(generateTeacher()));


        this.mockMvc.perform(get("/lessons/find")
                .param("startDate", LocalDateTime.now().toString())
                .param("endDate", LocalDateTime.now().toString())
                .param("teacherId", "1"))
                .andExpect(status().isOk());

        verify(lessonService).findTeacherLessonWeeks(any(LocalDateTime.class), any(LocalDateTime.class), any());
    }

    @Test
    void findLessonsShouldThrowException() throws Exception {
        doThrow(PersonNotFoundException.class).when(teacherService).findById(anyInt());

        this.mockMvc.perform(get("/lessons/find")
                .param("startDate", LocalDateTime.now().toString())
                .param("endDate", LocalDateTime.now().toString())
                .param("teacherId", "1"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof LessonSearchException));
    }

    @Test
    void performGetShowLessonShouldReturn200StatusWithAttributes() throws Exception {
        when(lessonService.findById(anyInt())).thenReturn(generateLesson());
        when(teacherService.findAll()).thenReturn(Collections.singletonList(generateTeacher()));
        when(groupService.findAll()).thenReturn(Collections.singletonList(generateGroup()));
        when(courseService.findAll()).thenReturn(Collections.singletonList(generateCourse()));
        when(classroomService.findAll()).thenReturn(Collections.singletonList(generateClassroom()));

        MockHttpServletRequestBuilder request = get("/lessons/show/{id}", 1);

        this.mockMvc
                .perform(request)
                .andExpect(model().attributeExists("groups", "courses", "teachers", "classrooms", "lesson"))
                .andExpect(status().isOk())
                .andExpect(view().name("lessons/show"));
    }

    @Test
    void performGetCreateLessonShouldReturnOkStatus() throws Exception {
        when(teacherService.findAll()).thenReturn(Collections.singletonList(generateTeacher()));
        when(groupService.findAll()).thenReturn(Collections.singletonList(generateGroup()));
        when(courseService.findAll()).thenReturn(Collections.singletonList(generateCourse()));
        when(classroomService.findAll()).thenReturn(Collections.singletonList(generateClassroom()));


        MockHttpServletRequestBuilder request = get("/lessons/create");

        this.mockMvc.perform(request)
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("groups", "courses", "teachers", "classrooms", "addRequest"))
                .andExpect(view().name("lessons/add"));

        verify(classroomService).findAll();
        verify(teacherService).findAll();
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
                .withLinkedin("https://test.ru")
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
