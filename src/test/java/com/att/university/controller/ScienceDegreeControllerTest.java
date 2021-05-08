package com.att.university.controller;

import com.att.university.entity.ScienceDegree;
import com.att.university.exception.ExceptionHandlerAdvice;
import com.att.university.request.science_degree.ScienceDegreeAddRequest;
import com.att.university.request.science_degree.ScienceDegreeUpdateRequest;
import com.att.university.service.ScienceDegreeService;
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
class ScienceDegreeControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ScienceDegreeService scienceDegreeService;

    @InjectMocks
    private ScienceDegreeController controller;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new ExceptionHandlerAdvice())
                .build();
    }

    @Test
    void performGetScienceDegreesShouldReturnOkStatus() throws Exception {
        when(scienceDegreeService.findAll()).thenReturn(generateScienceDegrees());

        this.mockMvc.perform(get("/sciencedegrees/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("scienceDegrees"));

        verify(scienceDegreeService).findAll();
    }

    @Test
    void performGetShowScienceDegreeShouldReturnOkStatus() throws Exception {
        when(scienceDegreeService.findById(anyInt())).thenReturn(generateScienceDegrees().get(0));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/sciencedegrees/show/{id}", 1);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("scienceDegree"))
                .andExpect(model().attributeExists("updateRequest"));

        verify(scienceDegreeService).findById(anyInt());
    }

    @Test
    void performPostUpdateScienceDegreeShouldReturn302Status() throws Exception {
        ScienceDegreeUpdateRequest updateRequest = new ScienceDegreeUpdateRequest(1, "name");

        doNothing().when(scienceDegreeService).update(updateRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/sciencedegrees/update")
                .flashAttr("scienceDegreeUpdateRequest", updateRequest);

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/sciencedegrees/show/1"));

        verify(scienceDegreeService).update(updateRequest);
    }

    @Test
    void performGetCreateScienceDegreeShouldReturnOkStatus() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/sciencedegrees/create");

        this.mockMvc.perform(request)
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("addRequest"))
                .andExpect(view().name("science_degrees/add"));
    }

    @Test
    void performPostStoreScienceDegreeShouldReturn302Status() throws Exception {
        ScienceDegreeAddRequest addRequest = new ScienceDegreeAddRequest("new");

        doNothing().when(scienceDegreeService).create(addRequest);


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/sciencedegrees/store")
                .flashAttr("scienceDegreeAddRequest", addRequest);

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(flash().attributeExists("successCreate"));

        verify(scienceDegreeService).create(addRequest);
    }

    @Test
    void performPostDeleteScienceDegreeShouldReturn302Status() throws Exception {
        doNothing().when(scienceDegreeService).deleteById(anyInt());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/sciencedegrees/delete")
                .param("id", "1");

        this.mockMvc.perform(request)
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/sciencedegrees/"));

        verify(scienceDegreeService).deleteById(anyInt());
    }


    private List<ScienceDegree> generateScienceDegrees() {
        return Arrays.asList(
                new ScienceDegree(1, "degree1"),
                new ScienceDegree(2, "degree2"),
                new ScienceDegree(3, "degree3")
        );
    }
}
