package com.att.university.controller;

import com.att.university.entity.Building;
import com.att.university.request.building.BuildingAddRequest;
import com.att.university.request.building.BuildingUpdateRequest;
import com.att.university.service.BuildingService;
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
@WebMvcTest(controllers = BuildingController.class)
@AutoConfigureMockMvc(addFilters = false)
class BuildingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuildingService buildingService;

    @Test
    void performGetBuildingsShouldReturnOkStatus() throws Exception {
        when(buildingService.findAll()).thenReturn(generateBuildings());

        this.mockMvc.perform(get("/buildings/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("buildings"));

        verify(buildingService).findAll();
    }

    @Test
    void performGetShowBuildingShouldReturnOkStatus() throws Exception {
        when(buildingService.findById(anyInt())).thenReturn(generateBuildings().get(0));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/buildings/show/{id}", 1);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("building"))
                .andExpect(model().attributeExists("updateRequest"));

        verify(buildingService).findById(anyInt());
    }

    @Test
    void performPostUpdateBuildingShouldReturn302Status() throws Exception {
        BuildingUpdateRequest updateRequest = new BuildingUpdateRequest(1, "name");

        doNothing().when(buildingService).update(updateRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/buildings/update")
                .flashAttr("buildingUpdateRequest", updateRequest);

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/buildings/show/1"));

        verify(buildingService).update(updateRequest);
    }

    @Test
    void performGetCreateBuildingShouldReturnOkStatus() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/buildings/create");

        this.mockMvc.perform(request)
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("addRequest"))
                .andExpect(view().name("buildings/add"));
    }

    @Test
    void performPostStoreBuildingShouldReturn302Status() throws Exception {
        BuildingAddRequest addRequest = new BuildingAddRequest("new");

        doNothing().when(buildingService).create(addRequest);


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/buildings/store")
                .flashAttr("buildingAddRequest", addRequest);

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(flash().attributeExists("successCreate"));

        verify(buildingService).create(addRequest);
    }

    @Test
    void performDeleteBuildingShouldReturn302Status() throws Exception {
        doNothing().when(buildingService).deleteById(anyInt());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/buildings/delete")
                .param("id", "1");

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/buildings/"));

        verify(buildingService).deleteById(anyInt());
    }


    private List<Building> generateBuildings() {
        return Arrays.asList(
                new Building(1, "building1"),
                new Building(2, "building2"),
                new Building(3, "building3")
        );
    }
}
