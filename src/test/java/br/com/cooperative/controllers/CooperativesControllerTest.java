package br.com.cooperative.controllers;

import br.com.cooperative.models.Response.CooperativeResponse;
import br.com.cooperative.models.entities.Cooperative;
import br.com.cooperative.models.request.CooperativeRequest;
import br.com.cooperative.services.CooperativeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.cooperative.configs.CP.DELETE_MESSAGE;
import static br.com.cooperative.mock.EntitiesMock.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class CooperativesControllerTest {
    static String URL_BASIC = "/v1/cooperatives/";
    @InjectMocks
    private CooperativesController cooperativesController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CooperativeService service;
    private Cooperative cooperative;
    private CooperativeRequest cooperativeRequest;
    private CooperativeResponse cooperativeResponse;
    private PageImpl<CooperativeResponse> cooperativeResponsePage;

    @BeforeEach
    void setUp() {
        cooperative = COOPERATIVE;
        cooperativeRequest = COOPERATIVE_REQUEST;
        cooperativeResponse = COOPERATIVE_RESPONSE;
        cooperativeResponsePage = new PageImpl<>(List.of(cooperativeResponse));
    }


    @Test
    @DisplayName("Should save cooperative with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void save() throws Exception {
        given(service.saveUpdate(any())).willReturn(cooperativeResponse);
        String jsonBody = new ObjectMapper().writeValueAsString(cooperativeRequest);
        ResultActions result =
                mockMvc
                        .perform(post(URL_BASIC)
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$").exists());
        result.andExpect(jsonPath("$.id").isNotEmpty());
        verify(service, times(1)).saveUpdate(any());
    }

    @Test
    @DisplayName("Should update cooperative with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void update() throws Exception {
        given(service.saveUpdate(any())).willReturn(cooperativeResponse);
        String jsonBody = new ObjectMapper().writeValueAsString(cooperativeRequest);
        ResultActions result =
                mockMvc
                        .perform(put(URL_BASIC + "{id}", ID_EXIST)
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$").exists());
        result.andExpect(jsonPath("$.id").isNotEmpty());
        verify(service, times(1)).saveUpdate(any());
    }

    @Test
    @DisplayName("Should fetch all cooperatives with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAll() throws Exception {
        given(service.findAll()).willReturn(List.of(cooperativeResponse));
        mockMvc.perform(get(URL_BASIC))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].id").isNotEmpty());
    }

    @Test
    @DisplayName("Should fetch one cooperative by id")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findById() throws Exception {
        given(service.findById(any())).willReturn(cooperativeResponse);
        this.mockMvc
                .perform(get(URL_BASIC + "{id}", ID_EXIST))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNotEmpty());
        verify(service, times(1)).findById(any());
    }

    @Test
    @DisplayName("Should delete an object with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void delete() throws Exception {
        given(service.delete(any())).willReturn("Bank" + DELETE_MESSAGE);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete(URL_BASIC + "{id}", ID_EXIST))
                .andExpect(status().isOk())
                .andExpect(content().string("Bank" + DELETE_MESSAGE));
        verify(service, times(1)).delete(any());
    }

    @Test
    @DisplayName("Should fetch a list of cooperatives with pageable and search with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllUserWithSearch() throws Exception {
        given(service.findAllWithPageAndSearch(any(), (Pageable) any())).willReturn(cooperativeResponsePage);
        this.mockMvc
                .perform(get(URL_BASIC + "/seek"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists());
        verify(service, times(1)).findAllWithPageAndSearch(any(), (Pageable) any());
    }

}