package com.att.university.controller;

import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import com.att.university.request.group.GroupAddRequest;
import com.att.university.request.group.GroupUpdateRequest;
import com.att.university.service.FacultyService;
import com.att.university.service.GroupService;
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
@WebMvcTest(controllers = GroupController.class)
@AutoConfigureMockMvc(addFilters = false)
class GroupControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    @MockBean
    private FacultyService facultyService;

    @Test
    void performGetGroupsShouldReturnOkStatus() throws Exception {
        when(groupService.findAll()).thenReturn(generateGroups());

        this.mockMvc.perform(get("/groups/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("groups"))
                .andExpect(view().name("groups/all"));

        verify(groupService).findAll();
    }

    @Test
    void performGetShowGroupShouldReturnOkStatus() throws Exception {
        when(groupService.findById(anyInt())).thenReturn(generateGroups().get(0));
        when(facultyService.findAll()).thenReturn(generateFaculties());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/groups/show/{id}", 1);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("group"))
                .andExpect(model().attributeExists("updateRequest"))
                .andExpect(model().attributeExists("faculties"));

        verify(groupService).findById(anyInt());
        verify(facultyService).findAll();
    }

    @Test
    void performPostUpdateCourseShouldReturn302Status() throws Exception {
        GroupUpdateRequest updateRequest = new GroupUpdateRequest(1, "name", 1);

        doNothing().when(groupService).update(updateRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/groups/update")
                .flashAttr("groupUpdateRequest", updateRequest);

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/groups/show/1"));

        verify(groupService).update(updateRequest);
    }

    @Test
    void performGetCreateCourseShouldReturnOkStatus() throws Exception {
        when(facultyService.findAll()).thenReturn(generateFaculties());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/groups/create");

        this.mockMvc.perform(request)
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("addRequest"))
                .andExpect(view().name("groups/add"));

        verify(facultyService).findAll();
    }

    @Test
    void performPostStoreGroupShouldReturn302Status() throws Exception {
        GroupAddRequest addRequest = new GroupAddRequest("new", 1);

        doNothing().when(groupService).create(addRequest);


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/groups/store")
                .flashAttr("groupAddRequest", addRequest);

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(flash().attributeExists("successCreate"));

        verify(groupService).create(addRequest);
    }

    @Test
    void performDeleteCourseShouldReturn302Status() throws Exception {
        doNothing().when(groupService).deleteById(anyInt());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/groups/delete")
                .param("id", "1");

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/groups/"));

        verify(groupService).deleteById(anyInt());
    }

    private List<Group> generateGroups() {
        return Arrays.asList(
                new Group(null, "GT-23", new Faculty(1, "School of Visual arts")),
                new Group(null, "HT-22", new Faculty(2, "Department of Geography"))
        );
    }

    private List<Faculty> generateFaculties() {
        return generateGroups().stream().map(Group::getFaculty).collect(Collectors.toList());
    }
}
