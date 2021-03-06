package com.att.controller;

import com.att.entity.AcademicRank;
import com.att.entity.ScienceDegree;
import com.att.entity.Teacher;
import com.att.exception.dao.AcademicRankNotFoundException;
import com.att.exception.dao.ScienceDegreeNotFoundException;
import com.att.exception.service.EmailAlreadyExistsException;
import com.att.exception.service.LoginFailException;
import com.att.request.person.teacher.TeacherRegisterRequest;
import com.att.request.person.teacher.TeacherUpdateRequest;
import com.att.service.AcademicRankService;
import com.att.service.ScienceDegreeService;
import com.att.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TeacherController.class)
@AutoConfigureMockMvc(addFilters = false)
class TeacherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private AcademicRankService academicRankService;

    @MockBean
    private ScienceDegreeService scienceDegreeService;

    @Test
    void performGetTeacherCreateShouldReturnOkStatus() throws Exception {
        this.mockMvc.perform(get("/teachers/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("registerRequest"));
    }

    @Test
    void performPostTeacherRegisterRequestShouldThrowEmailAlreadyExistsException() throws Exception {
        doThrow(EmailAlreadyExistsException.class).when(teacherService).register(any(TeacherRegisterRequest.class));

        this.mockMvc.perform(post("/teachers/register").param("email", "test@test.ru"))
                .andExpect(status().is(302))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmailAlreadyExistsException));
    }

    @Test
    void performPostTeacherRegisterRequestShouldReturnErrorModelViewIfRuntimeException() throws Exception {
        doThrow(RuntimeException.class).when(teacherService).register(any(TeacherRegisterRequest.class));

        this.mockMvc.perform(post("/teachers/register").param("email", "test@test.ru"))
                .andExpect(view().name("errors/error"));
    }

    @Test
    void performPostTeacherRegisterRequestShouldThrowNullPointerException() throws Exception {
        doThrow(NullPointerException.class).when(teacherService).register(any(TeacherRegisterRequest.class));

        this.mockMvc.perform(post("/teachers/register").param("email", "test@test.ru"))
                .andExpect(status().is(302))
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof NullPointerException));
    }

    @Test
    void performPostTeacherRegisterShouldReturn302Status() throws Exception {
        doNothing().when(teacherService).register(any(TeacherRegisterRequest.class));

        this.mockMvc.perform(post("/teachers/register").param("email", "test@test.ru"))
                .andExpect(status().is(302));
    }

    @Test
    void performGetShowLoginShouldReturn200Status() throws Exception {
        this.mockMvc
                .perform(get("/teachers/showLogin"))
                .andExpect(status().isOk());
    }

    @Test
    void performLoginPostRequestShouldReturn302Status() throws Exception {
        when(teacherService.login(anyString(), anyString())).thenReturn(true);
        when(teacherService.findByEmail(anyString())).thenReturn(generateTeacher());

        this.mockMvc
                .perform(
                        post("/teachers/login")
                                .param("email", "test@test.ru")
                                .param("password", "password")
                )
                .andExpect(model().attributeExists("id"))
                .andExpect(status().is(302));
    }

    @Test
    void performLoginPostRequestShouldReturn302StatusIfLoginAndPasswordAreIncorrect() throws Exception {
        when(teacherService.login(anyString(), anyString())).thenReturn(false);

        this.mockMvc
                .perform(
                        post("/teachers/login")
                                .param("email", "test@test.ru")
                                .param("password", "password")
                )
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof LoginFailException));
    }

    @Test
    void performGetShowTeacherShouldReturn200StatusWithAttributes() throws Exception {
        when(teacherService.findById(anyInt())).thenReturn(generateTeacher());
        when(academicRankService.findAll()).thenReturn(generateAcademicRanks());
        when(scienceDegreeService.findAll()).thenReturn(generateScienceDegrees());

        this.mockMvc
                .perform(get("/teachers/show").param("id", "1"))
                .andExpect(model().attributeExists("academicRanks"))
                .andExpect(model().attributeExists("scienceDegrees"))
                .andExpect(model().attributeExists("teacher"))
                .andExpect(status().isOk());
    }

    @Test
    void performShowAllGetRequestTeacherShouldReturn200StatusWithAttributes() throws Exception {
        Page<Teacher> teachers = new PageImpl<>(Collections.singletonList(generateTeacher()));

        when(teacherService.findAll(any(PageRequest.class))).thenReturn(teachers);

        int page = 1;
        int countTeachers = 2;

        this.mockMvc
                .perform(get("/teachers/{page}/{count}", page, countTeachers))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists( "teachers"))
                .andReturn();
    }

    @Test
    void performPostUpdateTeacherShouldReturn302Status() throws Exception {
        TeacherUpdateRequest updateRequest = generateUpdateRequest();

        doNothing().when(teacherService).update(updateRequest);

        MockHttpServletRequestBuilder request = post("/teachers/update")
                .flashAttr("teacherUpdateRequest", updateRequest);

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/teachers/show?id=1"));

        verify(teacherService).update(updateRequest);
    }

    @Test
    void performPostTeacherUpdateRequestShouldThrowAcademicRankNotFoundException() throws Exception {
        doThrow(AcademicRankNotFoundException.class).when(teacherService).update(any(TeacherUpdateRequest.class));

        this.mockMvc.perform(post("/teachers/update").param("email", "test@test.ru"))
                .andExpect(status().is(302))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AcademicRankNotFoundException));
    }

    @Test
    void performPostTeacherUpdateRequestShouldThrowScienceDegreeNotFoundException() throws Exception {
        doThrow(ScienceDegreeNotFoundException.class).when(teacherService).update(any(TeacherUpdateRequest.class));

        this.mockMvc.perform(post("/teachers/update").param("email", "test@test.ru"))
                .andExpect(status().is(302))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ScienceDegreeNotFoundException));
    }

    @Test
    void performDeleteTeacherShouldReturn302Status() throws Exception {
        doNothing().when(teacherService).deleteById(anyInt());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/teachers/delete")
                .param("id", "1");

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/teachers/1/3"));

        verify(teacherService).deleteById(anyInt());
    }

    private TeacherUpdateRequest generateUpdateRequest() {
        return TeacherUpdateRequest.builder()
                .withId(1)
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test@test.ru")
                .withLinkedin("https://test.ru")
                .withAcademicRankId(1)
                .withScienceDegreeId(1)
                .build();
    }

    private List<AcademicRank> generateAcademicRanks() {
        return Arrays.asList(
                generateRank(),
                generateRank(),
                generateRank()
        );
    }

    private List<ScienceDegree> generateScienceDegrees() {
        return Arrays.asList(
                generateDegree(),
                generateDegree(),
                generateDegree()
        );
    }

    private Teacher generateTeacher() {
        AcademicRank academicRank = generateRank();
        ScienceDegree scienceDegree = generateDegree();

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

    private AcademicRank generateRank() {
        return new AcademicRank(1, "testRank");
    }

    private ScienceDegree generateDegree() {
        return new ScienceDegree(1, "testDegree");
    }
}
