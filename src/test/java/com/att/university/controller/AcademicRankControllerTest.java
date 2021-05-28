package com.att.university.controller;

import com.att.university.entity.AcademicRank;
import com.att.university.request.academic_rank.AcademicRankAddRequest;
import com.att.university.request.academic_rank.AcademicRankUpdateRequest;
import com.att.university.service.AcademicRankService;
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
@WebMvcTest(controllers = AcademicRankController.class)
@AutoConfigureMockMvc(addFilters = false)
class AcademicRankControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AcademicRankService academicRankService;

    @Test
    void performGetAcademicRanksShouldReturnOkStatus() throws Exception {
        when(academicRankService.findAll()).thenReturn(generateAcademicRanks());

        this.mockMvc.perform(get("/academicranks/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("academicRanks"));

        verify(academicRankService).findAll();
    }

    @Test
    void performGetShowAcademicRankShouldReturnOkStatus() throws Exception {
        when(academicRankService.findById(anyInt())).thenReturn(generateAcademicRanks().get(0));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/academicranks/show/{id}", 1);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("academicRank"))
                .andExpect(model().attributeExists("updateRequest"));

        verify(academicRankService).findById(anyInt());
    }

    @Test
    void performPostUpdateAcademicRankShouldReturn302Status() throws Exception {
        AcademicRankUpdateRequest updateRequest = new AcademicRankUpdateRequest(1, "name");

        doNothing().when(academicRankService).update(updateRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/academicranks/update")
                .flashAttr("academicRankUpdateRequest", updateRequest);

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/academicranks/show/1"));

        verify(academicRankService).update(updateRequest);
    }

    @Test
    void performGetCreateAcademicRankShouldReturnOkStatus() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/academicranks/create");

        this.mockMvc.perform(request)
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("addRequest"))
                .andExpect(view().name("academic_ranks/add"));
    }

    @Test
    void performPostStoreAcademicRankShouldReturn302Status() throws Exception {
        AcademicRankAddRequest addRequest = new AcademicRankAddRequest("new");

        doNothing().when(academicRankService).create(addRequest);


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/academicranks/store")
                .flashAttr("academicRankAddRequest", addRequest);

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(flash().attributeExists("successCreate"));

        verify(academicRankService).create(addRequest);
    }

    @Test
    void performDeleteAcademicRankShouldReturn302Status() throws Exception {
        doNothing().when(academicRankService).deleteById(anyInt());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/academicranks/delete")
                .param("id", "1");

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/academicranks/"));

        verify(academicRankService).deleteById(anyInt());
    }


    private List<AcademicRank> generateAcademicRanks() {
        return Arrays.asList(
                new AcademicRank(1, "rank1"),
                new AcademicRank(2, "rank2"),
                new AcademicRank(3, "rank3")
        );
    }
}
