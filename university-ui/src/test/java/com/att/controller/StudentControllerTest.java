package com.att.controller;

import com.att.entity.Faculty;
import com.att.entity.Group;
import com.att.entity.Student;
import com.att.exception.dao.GroupNotFoundException;
import com.att.exception.service.EmailAlreadyExistsException;
import com.att.exception.service.LoginFailException;
import com.att.request.person.student.StudentRegisterRequest;
import com.att.request.person.student.StudentUpdateRequest;
import com.att.service.GroupService;
import com.att.service.StudentService;
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
@WebMvcTest(controllers = StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private GroupService groupService;

    @Test
    void performGetStudentCreateShouldReturnOkStatus() throws Exception {
        this.mockMvc.perform(get("/students/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("registerRequest"));
    }

    @Test
    void performPostStudentRegisterRequestShouldThrowEmailAlreadyExistsException() throws Exception {
        doThrow(EmailAlreadyExistsException.class).when(studentService).register(any(StudentRegisterRequest.class));

        this.mockMvc.perform(post("/students/register").param("email", "test@test.ru"))
                .andExpect(status().is(302))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmailAlreadyExistsException));
    }

    @Test
    void performPostStudentRegisterRequestShouldReturnErrorModelViewIfRuntimeException() throws Exception {
        doThrow(RuntimeException.class).when(studentService).register(any(StudentRegisterRequest.class));

        this.mockMvc.perform(post("/students/register").param("email", "test@test.ru"))
                .andExpect(view().name("errors/error"));
    }

    @Test
    void performPostStudentRegisterRequestShouldThrowNullPointerException() throws Exception {
        doThrow(NullPointerException.class).when(studentService).register(any(StudentRegisterRequest.class));

        this.mockMvc.perform(post("/students/register").param("email", "test@test.ru"))
                .andExpect(status().is(302))
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
    void performLoginPostRequestShouldReturn302StatusIfLoginAndPasswordAreIncorrect() throws Exception {
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
        Page<Student> students = new PageImpl<>(generateStudents());

        when(studentService.findAll(any(PageRequest.class))).thenReturn(students);

        int page = 1;
        int countStudents = 2;

        this.mockMvc
                .perform(get("/students/{page}/{count}", page, countStudents))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("students"))
                .andReturn();
    }

    @Test
    void performPostUpdateStudentShouldReturn302Status() throws Exception {
        StudentUpdateRequest updateRequest = StudentUpdateRequest.builder()
                .withId(1)
                .withFirstName("name")
                .withLastName("last")
                .withEmail("email")
                .withGroupId(1)
                .build();

        doNothing().when(studentService).update(updateRequest);

        MockHttpServletRequestBuilder request = post("/students/update")
                .flashAttr("studentUpdateRequest", updateRequest);

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/students/show?id=1"));

        verify(studentService).update(updateRequest);
    }

    @Test
    void performPostStudentUpdateRequestShouldThrowGroupNotFoundException() throws Exception {
        doThrow(GroupNotFoundException.class).when(studentService).update(any(StudentUpdateRequest.class));

        this.mockMvc.perform(post("/students/update").param("email", "test@test.ru"))
                .andExpect(status().is(302))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof GroupNotFoundException));
    }

    @Test
    void performDeleteStudentShouldReturn302Status() throws Exception {
        doNothing().when(studentService).delete(anyInt());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/students/delete")
                .param("id", "1");

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/students/1/3"));

        verify(studentService).delete(anyInt());
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
