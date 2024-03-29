package br.com.cooperative.controllers;

import br.com.cooperative.models.Response.UserResponse;
import br.com.cooperative.models.entities.User;
import br.com.cooperative.models.request.ChangePasswordRequest;
import br.com.cooperative.models.request.UserRequest;
import br.com.cooperative.services.UserService;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class UsersControllerTest {
    static String URL_BASIC = "/v1/users";
    @InjectMocks
    private UsersController usersController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ModelMapper mapper;
    @MockBean
    private UserService service;

    private UserResponse userResponse;
    private UserRequest userRequest;
    private ChangePasswordRequest changePasswordRequest;
    private PageImpl<UserResponse> userPage;
    private User user;


    @BeforeEach
    void setUp() {
        user = USER;
        userResponse = USER_RESPONSE;
        userRequest = USER_REQUEST;
        userPage = new PageImpl<>(List.of(userResponse));
        changePasswordRequest = CHANGE_PASSWORD_REQUEST;
    }

    @Test
    @DisplayName("Should fetch a list of users")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllUsers() throws Exception {
        given(service.findAllListed()).willReturn(List.of(userResponse));
        mockMvc.perform(get(URL_BASIC))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].id").isNotEmpty());
    }

    @Test
    @DisplayName("Should fetch one user by id")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findById() throws Exception {
        given(service.findById(any())).willReturn(userResponse);
        this.mockMvc
                .perform(get(URL_BASIC + "/{id}", ID_EXIST))
                .andDo(print())
                .andExpect(status().isOk());
        verify(service, times(1)).findById(any());
    }


    @Test
    @DisplayName("Should save a user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void save() throws Exception {
        given(service.save(user)).willReturn(userResponse);
        String jsonBody = new ObjectMapper().writeValueAsString(userRequest);
        ResultActions result =
                mockMvc
                        .perform(post(URL_BASIC)
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$").exists());
        result.andExpect(jsonPath("$.id").isNotEmpty());
        result.andExpect(jsonPath("$.userName").value("Jose"));
        verify(service, times(1)).save(user);
    }

    @Test
    @DisplayName("Should delete an object with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void delete() throws Exception {
        given(service.delete(any())).willReturn("User" + DELETE_MESSAGE);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete(URL_BASIC + "/{id}", ID_EXIST))
                .andExpect(status().isOk())
                .andExpect(content().string("User" + DELETE_MESSAGE));
        verify(service, times(1)).delete(any());
    }


    @Test
    @DisplayName("Should update a user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void update() throws Exception {
        given(service.update(any())).willReturn(userResponse);
        String jsonBody = new ObjectMapper().writeValueAsString(userRequest);
        ResultActions result =
                mockMvc
                        .perform(put(URL_BASIC + "/{id}", ID_EXIST)
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$").exists());
        result.andExpect(jsonPath("$.id").isNotEmpty());
        result.andExpect(jsonPath("$.userName").value("Jose"));
        verify(service, times(1)).update(any());
    }

    @Test
    @DisplayName("Should change the password with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void changePassowrd() throws Exception {
//        given(service.changePassword(any())).willReturn("The password was changed with success of the user: " + user.getEmail());
//        String jsonBody = new ObjectMapper().writeValueAsString(changePasswordRequest);
//        ResultActions result =
//                mockMvc
//                        .perform(put(URL_BASIC + "change-password")
//                                .content(jsonBody)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON));
//
//        result
//                .andExpect(status().isOk())
//                .andExpect(content().string("The password was changed with success of the user: " + user.getEmail()));
//        verify(service, times(1)).changePassword(any());
    }

//    @Test
//    @DisplayName("Should fetch a list of user with pageable and search with success")
//    @EnabledForJreRange(min = JRE.JAVA_17)
//    void findAllUserWithSearch() throws Exception {
//        given(service.findAllWithPageAndSearch(any(), (Pageable) any())).willReturn(userPage);
//        this.mockMvc
//                .perform(get(URL_BASIC + "/seek"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$").exists());
//        verify(service, times(1)).findAllWithPageAndSearch(any(), (Pageable) any());
//    }
}