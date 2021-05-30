package com.att.university.controller;

import com.att.university.entity.Faculty;
import com.att.university.request.faculty.FacultyAddRequest;
import com.att.university.request.faculty.FacultyUpdateRequest;
import com.att.university.service.FacultyService;
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
@WebMvcTest(controllers = FacultyController.class)
@AutoConfigureMockMvc(addFilters = false)
class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @Test
    void performGetFacultiesShouldReturnOkStatus() throws Exception {
        when(facultyService.findAll()).thenReturn(generateFaculties());

        this.mockMvc.perform(get("/faculties/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("faculties"));

        verify(facultyService).findAll();
    }

    @Test
    void performGetShowFacultyShouldReturnOkStatus() throws Exception {
        when(facultyService.findById(anyInt())).thenReturn(generateFaculties().get(0));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/faculties/show/{id}", 1);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("faculty"))
                .andExpect(model().attributeExists("updateRequest"));

        verify(facultyService).findById(anyInt());
    }

    @Test
    void performPostUpdateFacultyShouldReturn302Status() throws Exception {
        FacultyUpdateRequest updateRequest = new FacultyUpdateRequest(1, "name");

        doNothing().when(facultyService).update(updateRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/faculties/update")
                .flashAttr("facultyUpdateRequest", updateRequest);

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/faculties/show/1"));

        verify(facultyService).update(updateRequest);
    }

    @Test
    void performGetCreateFacultyShouldReturnOkStatus() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/faculties/create");

        this.mockMvc.perform(request)
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("addRequest"))
                .andExpect(view().name("faculties/add"));
    }

    @Test
    void performPostStoreFacultyShouldReturn302Status() throws Exception {
        FacultyAddRequest addRequest = new FacultyAddRequest("new");

        doNothing().when(facultyService).create(addRequest);


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/faculties/store")
                .flashAttr("facultyAddRequest", addRequest);

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(flash().attributeExists("successCreate"));

        verify(facultyService).create(addRequest);
    }

    @Test
    void performDeleteFacultyShouldReturn302Status() throws Exception {
        doNothing().when(facultyService).deleteById(anyInt());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/faculties/delete")
                .param("id", "1");

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/faculties/"));

        verify(facultyService).deleteById(anyInt());
    }


    private List<Faculty> generateFaculties() {
        return Arrays.asList(
                new Faculty(1, "faculty1"),
                new Faculty(2, "faculty2"),
                new Faculty(3, "faculty3")
        );
    }
}
