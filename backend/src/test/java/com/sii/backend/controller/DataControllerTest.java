package com.sii.backend.controller;

import com.sii.backend.model.DataModel;
import com.sii.backend.repository.DataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class DataControllerTest {

    private static final String URL_BASE = "/api";
    private static final String FIELD_ONE = "test1";
    private static final String FIELD_TWO = "test2";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataRepository dataRepository;

    @Test
    @DirtiesContext
    void addData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                                {
                                                    "field": "%s",
                                                     "intentions": "getting a job"
                                                }
                                        """.formatted(FIELD_ONE)
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk());
        List<DataModel> data = dataRepository.findAll();
        assertEquals(1, data.size());
        assertEquals(FIELD_ONE, data.getFirst().getField());
    }

    @Test
    @DirtiesContext
    void addDataFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                          {
                                                    "field": "%s"
                                                }
                                        """.formatted(FIELD_ONE)
                        ))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
        List<DataModel> data = dataRepository.findAll();
        assertEquals(0, data.size());
    }

    @Test
    @DirtiesContext
    void getAllData() throws Exception {
        DataModel dataOne = DataModel.builder().id("1").field(FIELD_ONE).build();
        DataModel dataTwo = DataModel.builder().id("2").field(FIELD_TWO).build();
        dataRepository.saveAll(List.of(dataOne, dataTwo));
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].field").value(FIELD_ONE))
                .andExpect(jsonPath("$[1].field").value(FIELD_TWO));

    }

    @Test
    @DirtiesContext
    void getDataById() throws Exception {
        DataModel dataOne = DataModel.builder().id("1").field(FIELD_ONE).build();
        DataModel dataTwo = DataModel.builder().id("2").field(FIELD_TWO).build();
        dataRepository.saveAll(List.of(dataOne, dataTwo));

        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/find_by_id/" + dataOne.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.field").value(FIELD_ONE))
                .andExpect(jsonPath("$.id").value(dataOne.getId()));
    }

    @Test
    @DirtiesContext
    void getDataById_Fail() throws Exception {
        DataModel dataOne = DataModel.builder().id("1").field(FIELD_ONE).build();
        DataModel dataTwo = DataModel.builder().id("2").field(FIELD_TWO).build();
        dataRepository.saveAll(List.of(dataOne, dataTwo));

        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/find_by_id/3"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }


    @Test
    @DirtiesContext
    void editData() throws Exception {
        DataModel dataOne = DataModel.builder().id("1").field(FIELD_ONE).build();
        DataModel dataTwo = DataModel.builder().id("2").field(FIELD_TWO).build();
        dataRepository.saveAll(List.of(dataOne, dataTwo));

        mockMvc.perform(MockMvcRequestBuilders.put(URL_BASE + "/" + dataOne.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                                {
                                                    "field": "%s",
                                                     "intentions": "getting a job"
                                                }
                                        """.formatted(FIELD_TWO)
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk());
        List<DataModel> data = dataRepository.findAll();
        assertEquals(2, data.size());
        assertEquals(data.get(0).getField(), data.get(1).getField());
    }

    @Test
    @DirtiesContext
    void deleteData() throws Exception {
        DataModel dataOne = DataModel.builder().id("1").field(FIELD_ONE).build();
        DataModel dataTwo = DataModel.builder().id("2").field(FIELD_TWO).build();
        dataRepository.saveAll(List.of(dataOne, dataTwo));
        //Test that it is 2 of them
        assertEquals(2, dataRepository.findAll().size());

        mockMvc.perform(MockMvcRequestBuilders.delete(URL_BASE + "/" + dataOne.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        List<DataModel> actual = dataRepository.findAll();
        //Confirm delete
        assertEquals(1, actual.size());
    }
}