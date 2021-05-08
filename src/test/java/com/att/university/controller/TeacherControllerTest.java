package com.att.university.controller;

import com.att.university.config.H2Config;
import com.att.university.config.WebTestConfig;
import com.att.university.entity.AcademicRank;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Teacher;
import com.att.university.exception.ExceptionHandlerAdvice;
import com.att.university.exception.PersonHandleAdvice;
import com.att.university.exception.TeacherControllerAdvice;
import com.att.university.exception.dao.AcademicRankNotFoundException;
import com.att.university.exception.dao.ScienceDegreeNotFoundException;
import com.att.university.exception.service.EmailAlreadyExistsException;
import com.att.university.exception.service.LoginFailException;
import com.att.university.exception.service.NameIncorrectException;
import com.att.university.exception.service.PasswordTooShortException;
import com.att.university.exception.service.PasswordsAreNotTheSameException;
import com.att.university.exception.service.WrongEmailFormatException;
import com.att.university.request.person.teacher.TeacherRegisterRequest;
import com.att.university.request.person.teacher.TeacherUpdateRequest;
import com.att.university.service.AcademicRankService;
import com.att.university.service.ScienceDegreeService;
import com.att.university.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
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
@ContextConfiguration(classes = {H2Config.class, WebTestConfig.class})
@WebAppConfiguration
class TeacherControllerTest {
    @Value("${person.default.page}")
    private int defaultPage;

    @Value("${person.default.items}")
    private int itemsOnPage;

    private MockMvc mockMvc;

    @Mock
    private TeacherService teacherService;

    @Mock
    private AcademicRankService academicRankService;

    @Mock
    private ScienceDegreeService scienceDegreeService;

    @InjectMocks
    private TeacherController teacherController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(teacherController)
                .setControllerAdvice(new TeacherControllerAdvice(), new PersonHandleAdvice(), 
                        new ExceptionHandlerAdvice())
                .build();
    }

    @Test
    void performGetTeacherCreateShouldReturnOkStatus() throws Exception {
        this.mockMvc.perform(get("/teachers/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("registerRequest"));
    }

    @Test
    void performPostTeacherRegisterRequestShouldThrowWrongEmailFormatException() throws Exception {
        doThrow(WrongEmailFormatException.class).when(teacherService).register(any(TeacherRegisterRequest.class));

        this.mockMvc.perform(post("/teachers/register").param("email", "test@test.ru"))
                .andExpect(status().is(302))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof WrongEmailFormatException));
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
    void performPostTeacherRegisterRequestShouldThrowPasswordTooShortException() throws Exception {
        doThrow(PasswordTooShortException.class).when(teacherService).register(any(TeacherRegisterRequest.class));

        this.mockMvc.perform(post("/teachers/register").param("email", "test@test.ru"))
                .andExpect(status().is(302))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PasswordTooShortException));
    }

    @Test
    void performPostTeacherRegisterRequestShouldThrowNameIncorrectException() throws Exception {
        doThrow(NameIncorrectException.class).when(teacherService).register(any(TeacherRegisterRequest.class));

        this.mockMvc.perform(post("/teachers/register").param("email", "test@test.ru"))
                .andExpect(status().is(302))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NameIncorrectException));
    }

    @Test
    void performPostTeacherRegisterRequestShouldThrowPasswordsAreNotTheSameException() throws Exception {
        doThrow(PasswordsAreNotTheSameException.class).when(teacherService).register(any(TeacherRegisterRequest.class));

        this.mockMvc.perform(post("/teachers/register").param("email", "test@test.ru"))
                .andExpect(status().is(302))
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof PasswordsAreNotTheSameException));
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
        when(teacherService.findAll(anyInt(), anyInt())).thenReturn(generateTeachers());
        when(academicRankService.findAll()).thenReturn(generateAcademicRanks());
        when(scienceDegreeService.findAll()).thenReturn(generateScienceDegrees());

        int page = 1;
        int countTeachers = 2;

        this.mockMvc
                .perform(get("/teachers/{page}/{count}", page, countTeachers))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("countPages", "page", "count", "teachers"))
                .andReturn();
    }

    @Test
    void performPostUpdateTeacherShouldReturn302Status() throws Exception {
        TeacherUpdateRequest updateRequest = generateUpdateRequest();

        doNothing().when(teacherService).update(updateRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/teachers/update")
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
        ReflectionTestUtils.setField(teacherController, "itemsOnPage", itemsOnPage);
        ReflectionTestUtils.setField(teacherController, "defaultPage", defaultPage);

        doNothing().when(teacherService).deleteById(anyInt());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/teachers/delete")
                .param("id", "1");

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/teachers/1/3"));

        verify(teacherService).deleteById(anyInt());
    }

    private TeacherRegisterRequest generateRegisterRequest() {
        return TeacherRegisterRequest.builder()
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test@test.ru")
                .withPassword("1234567890")
                .withLinkedin("http://test.ru")
                .withAcademicRankId(1)
                .withScienceDegreeId(1)
                .build();
    }

    private TeacherUpdateRequest generateUpdateRequest() {
        return TeacherUpdateRequest.builder()
                .withId(1)
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test@test.ru")
                .withLinkedin("http://test.ru")
                .withAcademicRankId(1)
                .withScienceDegreeId(1)
                .build();
    }

    private List<Teacher> generateTeachers() {
        return Arrays.asList(
               generateTeacher(),
               generateTeacher(),
               generateTeacher()
        );
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
                .withLinkedin("http://test.ru")
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
