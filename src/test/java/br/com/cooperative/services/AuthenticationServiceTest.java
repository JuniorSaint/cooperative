package br.com.cooperative.services;

import br.com.cooperative.configs.security.JwtService;
import br.com.cooperative.models.Response.AuthenticationResponse;
import br.com.cooperative.models.entities.User;
import br.com.cooperative.models.request.AuthenticationRequest;
import br.com.cooperative.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;

import static br.com.cooperative.mock.EntitiesMock.*;

class AuthenticationServiceTest {
    @InjectMocks
    private AuthenticationService service;
    @Mock
    private UserRepository repository;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    private AuthenticationResponse authenticationResponse;
    private AuthenticationRequest authenticationRequest;
    private User user;

    @BeforeEach
    void setUp() {
        user = USER;
        authenticationRequest = AUTHENTICATION_REQUEST;
        authenticationResponse = AUTHENTICATION_RESPONSE;
    }

    @Test
    @DisplayName("")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void authenticate() {
    }

}