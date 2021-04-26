package com.att.university.controller;

import com.att.university.config.H2Config;
import com.att.university.config.WebTestConfig;
import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import com.att.university.entity.Student;
import com.att.university.exception.ExceptionHandlerAdvice;
import com.att.university.exception.PersonHandleAdvice;
import com.att.university.exception.StudentControllerAdvice;
import com.att.university.exception.service.EmailAlreadyExistsException;
import com.att.university.exception.service.LoginFailException;
import com.att.university.exception.service.NameIncorrectException;
import com.att.university.exception.service.PasswordTooShortException;
import com.att.university.exception.service.PasswordsAreNotTheSameException;
import com.att.university.exception.service.WrongEmailFormatException;
import com.att.university.request.person.student.StudentRegisterRequest;
import com.att.university.service.GroupService;
import com.att.university.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {H2Config.class, WebTestConfig.class})
@WebAppConfiguration
class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @Mock
    private GroupService groupService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(studentController)
                .setControllerAdvice(new StudentControllerAdvice(), new PersonHandleAdvice(), new ExceptionHandlerAdvice())
                .build();
    }

    @Test
    void performGetStudentCreateShouldReturnOkStatus() throws Exception {
        this.mockMvc.perform(get("/students/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("registerRequest"));
    }

    @Test
    void performPostStudentRegisterRequestShouldThrowWrongEmailFormatException() throws Exception {
        doThrow(WrongEmailFormatException.class).when(studentService).register(any(StudentRegisterRequest.class));

        this.mockMvc.perform(post("/students/register").param("email", "test@test.ru"))
                .andExpect(status().is(400))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof WrongEmailFormatException));
    }

    @Test
    void performPostStudentRegisterRequestShouldThrowEmailAlreadyExistsException() throws Exception {
        doThrow(EmailAlreadyExistsException.class).when(studentService).register(any(StudentRegisterRequest.class));

        this.mockMvc.perform(post("/students/register").param("email", "test@test.ru"))
                .andExpect(status().is(400))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmailAlreadyExistsException));
    }

    @Test
    void performPostStudentRegisterRequestShouldReturnErrorModelViewIfRuntimeException() throws Exception {
        doThrow(RuntimeException.class).when(studentService).register(any(StudentRegisterRequest.class));

        this.mockMvc.perform(post("/students/register").param("email", "test@test.ru"))
                .andExpect(status().is(500))
                .andExpect(view().name("errors/error"));
    }

    @Test
    void performPostStudentRegisterRequestShouldThrowPasswordTooShortException() throws Exception {
        doThrow(PasswordTooShortException.class).when(studentService).register(any(StudentRegisterRequest.class));

        this.mockMvc.perform(post("/students/register").param("email", "test@test.ru"))
                .andExpect(status().is(400))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PasswordTooShortException));
    }

    @Test
    void performPostStudentRegisterRequestShouldThrowNameIncorrectException() throws Exception {
        doThrow(NameIncorrectException.class).when(studentService).register(any(StudentRegisterRequest.class));

        this.mockMvc.perform(post("/students/register").param("email", "test@test.ru"))
                .andExpect(status().is(400))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NameIncorrectException));
    }

    @Test
    void performPostStudentRegisterRequestShouldThrowPasswordsAreNotTheSameException() throws Exception {
        doThrow(PasswordsAreNotTheSameException.class).when(studentService).register(any(StudentRegisterRequest.class));

        this.mockMvc.perform(post("/students/register").param("email", "test@test.ru"))
                .andExpect(status().is(302))
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof PasswordsAreNotTheSameException));
    }

    @Test
    void performPostStudentRegisterRequestShouldThrowNullPointerException() throws Exception {
        doThrow(NullPointerException.class).when(studentService).register(any(StudentRegisterRequest.class));

        this.mockMvc.perform(post("/students/register").param("email", "test@test.ru"))
                .andExpect(status().is(500))
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof NullPointerException));
    }

    @Test
    void performPostStudentRegisterShouldReturn302Status() throws Exception {
        doNothing().when(studentService).register(any(StudentRegisterRequest.class));

        this.mockMvc.perform(post("/students/register").param("email", "test@test.ru"))
                .andExpect(status().is(302));
    }

    @Test
    void performGetShowLoginShouldReturn200Status() throws Exception {
        this.mockMvc
                .perform(get("/students/showLogin"))
                .andExpect(status().isOk());
    }

    @Test
    void performLoginPostRequestShouldReturn302Status() throws Exception {
        when(studentService.login(anyString(), anyString())).thenReturn(true);
        when(studentService.findByEmail(anyString())).thenReturn(generateStudentEntity());

        this.mockMvc
                .perform(
                        post("/students/login")
                                .param("email", "test@test.ru")
                                .param("password", "password")
                )
                .andExpect(model().attributeExists("id"))
                .andExpect(status().is(302));
    }

    @Test
    void performLoginPostRequestShouldReturn400StatusIfLoginAndPasswordAreIncorrect() throws Exception {
        when(studentService.login(anyString(), anyString())).thenReturn(false);

        this.mockMvc
                .perform(
                        post("/students/login")
                                .param("email", "test@test.ru")
                                .param("password", "password")
                )
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof LoginFailException));
    }

    @Test
    void performGetShowStudentShouldReturn200StatusWithAttributes() throws Exception {
        when(studentService.findById(anyInt())).thenReturn(generateStudentEntity());
        when(groupService.findAll()).thenReturn(generateGroups());

        this.mockMvc
                .perform(get("/students/show").param("id", "1"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attributeExists("student"))
                .andExpect(status().isOk());
    }

    @Test
    void performShowAllGetRequestStudentShouldReturn200StatusWithAttributes() throws Exception {
        when(studentService.findAll(anyInt(), anyInt())).thenReturn(generateStudents());
        when(groupService.findAll()).thenReturn(generateGroups());

        int page = 1;
        int countStudents = 2;

        this.mockMvc
                .perform(get("/students/{page}/{count}", page, countStudents))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("countPages", "page", "count", "students"))
                .andReturn();
    }

    private List<Student> generateStudents() {
        return Arrays.asList(
                generateStudentEntity(),
                generateStudentEntity(),
                generateStudentEntity()
        );
    }

    private Student generateStudentEntity() {
        return Student.builder()
                .withId(1)
                .withFirstName("name")
                .withLastName("last")
                .withEmail("test@test.ru")
                .withPassword("123456789")
                .build();
    }

    private List<Group> generateGroups() {
        return Arrays.asList(
                new Group(1, "GT-23", new Faculty(1, "School of Visual arts")),
                new Group(2, "HT-22", new Faculty(2, "Department of Geography")),
                new Group(3, "HY-53", new Faculty(3, "Department of Plant Science"))
        );
    }
}
