package br.com.cooperative.controllers;

import br.com.cooperative.models.Response.BankResponse;
import br.com.cooperative.models.entities.Bank;
import br.com.cooperative.models.request.BankRequest;
import br.com.cooperative.services.BankService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class BanksControllerTest {

    static String URL_BASIC = "/v1/banks";
    @InjectMocks
    private BanksController banksController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ModelMapper mapper;
    @MockBean
    private BankService service;
    private Bank bank;
    private BankRequest bankRequest;
    private BankResponse bankResponse;
    private PageImpl<BankResponse> bankResponsePage;

    @BeforeEach
    void setUp() {
        bank = BANK;
        bankRequest = BANK_REQUEST;
        bankResponse = BANK_RESPONSE;
        bankResponsePage = new PageImpl<>(List.of(bankResponse));
    }

    @Test
    @DisplayName("Should save bank with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void save() throws Exception {
        when(mapper.map(any(), eq(Bank.class))).thenReturn(bank);
        given(service.saveUpdate(any(Bank.class))).willReturn(bankResponse);
        String jsonBody = new ObjectMapper().writeValueAsString(bank);
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
    @DisplayName("Should update bank with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void update() throws Exception{
        given(service.saveUpdate(any())).willReturn(bankResponse);
        String jsonBody = new ObjectMapper().writeValueAsString(bankRequest);
        ResultActions result =
                mockMvc
                        .perform(put(URL_BASIC + "/{id}", ID_EXIST)
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$").exists());
        result.andExpect(jsonPath("$.id").isNotEmpty());
        verify(service, times(1)).saveUpdate(any());
    }

    @Test
    @DisplayName("Should fetch all banks with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAll() throws Exception{
        given(service.findAll()).willReturn(List.of(bankResponse));
        mockMvc.perform(get(URL_BASIC))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].id").isNotEmpty());
    }

    @Test
    @DisplayName("Should fetch one bank by id")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findById() throws Exception {
        given(service.findById(any())).willReturn(bankResponse);
        this.mockMvc
                .perform(get(URL_BASIC + "/{id}", ID_EXIST))
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
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete(URL_BASIC + "/{id}", ID_EXIST))
                .andExpect(status().isOk())
                .andExpect(content().string("Bank" + DELETE_MESSAGE));
        verify(service, times(1)).delete(any());
    }
}