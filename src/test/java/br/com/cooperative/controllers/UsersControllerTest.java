package br.com.cooperative.controllers;

import br.com.cooperative.models.request.OnlyIdRequest;
import br.com.cooperative.models.request.RoleRequest;
import br.com.cooperative.models.request.UserRequest;
import br.com.cooperative.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {
    static String URL_BASIC = "/v1/users/";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService service;

    private RoleRequest roleRequest;
    private OnlyIdRequest onlyIdRequest;
    private UserRequest userRequest;
    private UUID existId;

    @BeforeEach
    void setUp() {
        LocalDate date = LocalDate.of(1970, 12, 21);
        UUID existId = UUID.fromString("f2e5aeb0-c519-418e-814d-3683852d3d17");
        roleRequest = new RoleRequest(UUID.fromString("fee5aeb0-c519-418e-814d-3633852d3d17"), "admin");
        onlyIdRequest = new OnlyIdRequest(UUID.fromString("f2e5aeb0-c519-418e-814d-3633852d3d17"));
        userRequest = UserRequest.builder().id(existId).userName("Jose").email("junior@junior.com").password("123456").active(true).cpf("885.885.885-00").roles(List.of(roleRequest)).cooperative(onlyIdRequest).build();
    }

    @Test
    @DisplayName("Should bring a list of users")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllUsers() throws Exception {
        mockMvc.perform(get(URL_BASIC))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Should bring one user by id")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findById() throws Exception {
        this.mockMvc
                .perform(get(URL_BASIC + "{id}", existId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }


    @Test
    @DisplayName("Should save a user with success")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void save() throws Exception {
        String json = new ObjectMapper().writeValueAsString(userRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URL_BASIC)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isCreated())
                .andDo(print())

        ;
    }

    @Test
    @DisplayName("fdgs")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void delete() {
    }

    @Test
    @DisplayName("gs")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void update() {
    }

    @Test
    @DisplayName("gdfg")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void changePassowrd() {
    }

    @Test
    @DisplayName("gsdfgs")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void findAllUserWithSearch() {
    }
}