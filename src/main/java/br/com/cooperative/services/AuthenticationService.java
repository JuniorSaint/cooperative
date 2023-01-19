package br.com.cooperative.services;

import br.com.cooperative.configs.security.JwtService;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.AuthenticationResponse;
import br.com.cooperative.models.request.AuthenticationRequest;
import br.com.cooperative.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    UserRepository repository;
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        return AuthenticationResponse.builder()
                .token(generateTheToken(request.getEmail()))
                .build();
    }
    private String generateTheToken(String email){
        return repository.findByEmail(email)
                .map(result -> jwtService.generateToken(result))
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }
}
