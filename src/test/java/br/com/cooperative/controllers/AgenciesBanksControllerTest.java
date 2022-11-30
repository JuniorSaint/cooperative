package br.com.cooperative.controllers;

import br.com.cooperative.models.Response.AgencyBankResponse;
import br.com.cooperative.models.entities.AgencyBank;
import br.com.cooperative.models.request.AgencyBankRequest;
import br.com.cooperative.services.AgencyBankService;
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
class AgenciesBanksControllerTest {
    static String URL_BASIC = "/v1/agencies-banks/";
    @InjectMocks
    private AgenciesBanksController agenciesBanksController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AgencyBankService service;
    private AgencyBank agencyBank;
    private AgencyBankRequest agencyBankRequest;
    private AgencyBankResponse agencyBankResponse;
    private PageImpl<AgencyBankResponse> agencyBankPage;

    @BeforeEach
    void setUp() {
        agencyBank = AGENCY_BANK;
        agencyBankRequest = AGENCY_BANK_REQUEST;
        agencyBankResponse = AGENCY_BANK_RESPONSE;
        agencyBankPage = new PageImpl<>(List.of(agencyBankResponse));
    }

    @Test
    @DisplayName("Should save agency bank with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void save() throws Exception {
        given(service.saveUpdate(any())).willReturn(agencyBankResponse);
        String jsonBody = new ObjectMapper().writeValueAsString(agencyBankRequest);
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
    @DisplayName("Should update agency bank with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void update() throws Exception{
        given(service.saveUpdate(any())).willReturn(agencyBankResponse);
        String jsonBody = new ObjectMapper().writeValueAsString(agencyBankRequest);
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
    @DisplayName("Should fetch all agencies banks with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAll() throws Exception{
        given(service.findAll()).willReturn(List.of(agencyBankResponse));
        mockMvc.perform(get(URL_BASIC))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].id").isNotEmpty());
    }

    @Test
    @DisplayName("Should fetch one agency bank by id")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findById() throws Exception {
        given(service.findById(any())).willReturn(agencyBankResponse);
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
        given(service.delete(any())).willReturn("Agency bank" + DELETE_MESSAGE);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete(URL_BASIC + "{id}", ID_EXIST))
                .andExpect(status().isOk())
                .andExpect(content().string("Agency bank" + DELETE_MESSAGE));
        verify(service, times(1)).delete(any());
    }

    @Test
    @DisplayName("Should fetch a list of agenies banks with pageable and search with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllUserWithSearch() throws Exception {
        given(service.findAllWithPageAndSearch(any(), (Pageable) any())).willReturn(agencyBankPage);
        this.mockMvc
                .perform(get(URL_BASIC + "seek"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists());
        verify(service, times(1)).findAllWithPageAndSearch(any(), (Pageable) any());
    }
}