package com.att.university.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = IndexController.class)
@AutoConfigureMockMvc(addFilters = false)
class IndexControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void performGetIndexShouldReturnOkStatus() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print())
                .andExpect(view().name("index"))
                .andExpect(status().isOk());
    }
}
