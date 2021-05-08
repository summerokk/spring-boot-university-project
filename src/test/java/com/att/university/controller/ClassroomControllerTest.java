package com.att.university.controller;

import com.att.university.entity.Building;
import com.att.university.entity.Classroom;
import com.att.university.exception.ExceptionHandlerAdvice;
import com.att.university.request.classroom.ClassroomAddRequest;
import com.att.university.request.classroom.ClassroomUpdateRequest;
import com.att.university.service.BuildingService;
import com.att.university.service.ClassroomService;
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

@ExtendWith(MockitoExtension.class)
class ClassroomControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ClassroomService classroomService;

    @Mock
    private BuildingService buildingService;

    @InjectMocks
    private ClassroomController controller;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new ExceptionHandlerAdvice())
                .build();
    }

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

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/classrooms/show/{id}", 1);

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

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/classrooms/create");

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
