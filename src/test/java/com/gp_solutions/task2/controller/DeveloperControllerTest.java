package com.gp_solutions.task2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gp_solutions.task2.model.Developer;
import com.gp_solutions.task2.service.DeveloperService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(DeveloperController.class)
class DeveloperControllerTest {

    private List<Developer> developerList = new ArrayList<>();
    private static Developer developer;
    private static ObjectMapper mapper = new ObjectMapper();


    @MockBean
    private DeveloperService developerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @BeforeAll
    static void setUp() {
        developer = new Developer(1L, "Olga", "olga@gmail.com");
    }

    @Test
    void findDeveloper() throws Exception {
        given(developerService.findDeveloper(developer.getId())).willReturn(developer);

        mockMvc.perform(get("/api/developers/{id}", developer.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(developer.getId().intValue())))
                .andExpect(jsonPath("$.name", is(developer.getName())))
                .andExpect(jsonPath("$.email", is(developer.getEmail())));
    }

    @Test
    void createDeveloper() throws Exception {
        String developerString = "{\"name\":\"Olga\",\"email\":\"olga@gmail.com\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/developers")
                .accept(MediaType.APPLICATION_JSON).content(developerString)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void updateDeveloper() throws Exception {
        developer.setEmail("newolga@mail.com");
        String json = mapper.writeValueAsString(developer);
        mockMvc.perform(put("/api/developers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteDeveloper() throws Exception {
        mockMvc.perform(delete("/api/developers/{id}", developer.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllDeveloper() throws Exception {
        developerList.add(developer);
        given(developerService.getAllDeveloper()).willReturn(developerList);
        mockMvc.perform(get("/api/developers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllDeveloperEmptyList() throws Exception {
        given(developerService.getAllDeveloper()).willReturn(developerList);
        mockMvc.perform(get("/api/developers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}