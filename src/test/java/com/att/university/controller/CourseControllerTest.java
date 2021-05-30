package com.att.university.controller;

import com.att.university.entity.Course;
import com.att.university.request.course.CourseAddRequest;
import com.att.university.request.course.CourseUpdateRequest;
import com.att.university.service.CourseService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    void performGetCoursesShouldReturnOkStatus() throws Exception {
        when(courseService.findAll()).thenReturn(generateCourses());

        this.mockMvc.perform(get("/courses/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("courses"));

        verify(courseService).findAll();
    }

    @Test
    void performGetShowCourseShouldReturnOkStatus() throws Exception {
        when(courseService.findById(anyInt())).thenReturn(generateCourses().get(0));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/courses/show/{id}", 1);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("course"))
                .andExpect(model().attributeExists("updateRequest"));

        verify(courseService).findById(anyInt());
    }

    @Test
    void performPostUpdateCourseShouldReturn302Status() throws Exception {
        CourseUpdateRequest updateRequest = new CourseUpdateRequest(1, "name");

        doNothing().when(courseService).update(updateRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/courses/update")
                .flashAttr("courseUpdateRequest", updateRequest);

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/courses/show/1"));

        verify(courseService).update(updateRequest);
    }

    @Test
    void performGetCreateCourseShouldReturnOkStatus() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/courses/create");

        this.mockMvc.perform(request)
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("addRequest"))
                .andExpect(view().name("courses/add"));
    }

    @Test
    void performPostStoreCourseShouldReturn302Status() throws Exception {
        CourseAddRequest addRequest = new CourseAddRequest("new");

        doNothing().when(courseService).create(addRequest);


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/courses/store")
                .flashAttr("courseAddRequest", addRequest);

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(flash().attributeExists("successCreate"));

        verify(courseService).create(addRequest);
    }

    @Test
    void performDeleteCourseShouldReturn302Status() throws Exception {
        doNothing().when(courseService).deleteById(anyInt());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/courses/delete")
                .param("id", "1");

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/courses/"));

        verify(courseService).deleteById(anyInt());
    }


    private List<Course> generateCourses() {
        return Arrays.asList(
                new Course(1, "Special Topics in Agronomy"),
                new Course(2, "Math"),
                new Course(3, "Biology")
        );
    }
}
