package com.att.controller;

import com.att.request.classroom.ClassroomAddRequest;
import com.att.request.classroom.ClassroomUpdateRequest;
import com.att.entity.Building;
import com.att.entity.Classroom;
import com.att.service.BuildingService;
import com.att.service.ClassroomService;
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
import java.util.stream.Collectors;

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
@WebMvcTest(controllers = ClassroomController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClassroomControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClassroomService classroomService;

    @MockBean
    private BuildingService buildingService;

    @Test
    void performGetClassroomsShouldReturnOkStatus() throws Exception {
        when(classroomService.findAll()).thenReturn(generateClassrooms());

        this.mockMvc.perform(get("/classrooms/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("classrooms"))
                .andExpect(view().name("classrooms/all"));

        verify(classroomService).findAll();
    }

    @Test
    void performGetShowClassroomShouldReturnOkStatus() throws Exception {
        when(classroomService.findById(anyInt())).thenReturn(generateClassrooms().get(0));
        when(buildingService.findAll()).thenReturn(generateBuildings());

        MockHttpServletRequestBuilder request = get("/classrooms/show/{id}", 1);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("classroom"))
                .andExpect(model().attributeExists("updateRequest"))
                .andExpect(model().attributeExists("buildings"));

        verify(classroomService).findById(anyInt());
        verify(buildingService).findAll();
    }

    @Test
    void performPostUpdateCourseShouldReturn302Status() throws Exception {
        ClassroomUpdateRequest updateRequest = new ClassroomUpdateRequest(1, 12, 1);

        doNothing().when(classroomService).update(updateRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/classrooms/update")
                .flashAttr("classroomUpdateRequest", updateRequest);

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/classrooms/show/1"));

        verify(classroomService).update(updateRequest);
    }

    @Test
    void performGetCreateCourseShouldReturnOkStatus() throws Exception {
        when(buildingService.findAll()).thenReturn(generateBuildings());

        MockHttpServletRequestBuilder request = get("/classrooms/create");

        this.mockMvc.perform(request)
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("addRequest"))
                .andExpect(view().name("classrooms/add"));

        verify(buildingService).findAll();
    }

    @Test
    void performPostStoreClassroomShouldReturn302Status() throws Exception {
        ClassroomAddRequest addRequest = new ClassroomAddRequest(12, 1);

        doNothing().when(classroomService).create(addRequest);


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/classrooms/store")
                .flashAttr("classroomAddRequest", addRequest);

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(flash().attributeExists("successCreate"));

        verify(classroomService).create(addRequest);
    }

    @Test
    void performDeleteCourseShouldReturn302Status() throws Exception {
        doNothing().when(classroomService).deleteById(anyInt());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/classrooms/delete")
                .param("id", "1");

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/classrooms/"));

        verify(classroomService).deleteById(anyInt());
    }

    private List<Classroom> generateClassrooms() {
        return Arrays.asList(
                new Classroom(null, 12, new Building(1, "address 1")),
                new Classroom(null, 14, new Building(2, "address 2"))
        );
    }

    private List<Building> generateBuildings() {
        return generateClassrooms().stream().map(Classroom::getBuilding).collect(Collectors.toList());
    }
}
