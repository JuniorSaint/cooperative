package br.com.cooperative.services;

import br.com.cooperative.configs.CP;
import br.com.cooperative.exceptions.BadRequestException;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.Response.TokenReponse;
import br.com.cooperative.models.request.AccountCredentialRequest;
import br.com.cooperative.repositories.UserRepository;
import br.com.cooperative.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository repository;

    @SuppressWarnings("rawtypes")
    public ResponseEntity signin(AccountCredentialRequest data) {
        try {
            var email = data.getEmail();
            var password = data.getPassword();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            var user = repository.findByEmail(email);

            var tokenResponse = new TokenReponse();
            if (user != null) {
                tokenResponse = tokenProvider.createAccessToken(email, user.get().getRoles());
            } else {
                throw new EntityNotFoundException("User" + CP.NOT_FOUND + " email: " + email);
            }
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw new BadRequestException("Invalid username/password supplied!");
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(String email, String refreshToken) {
        var user = repository.findByEmail(email);

        var tokenResponse = new TokenReponse();
        if (user != null) {
            tokenResponse = tokenProvider.refreshToken(refreshToken);
        } else {
            throw new EntityNotFoundException("User" + CP.NOT_FOUND + " email: " + email);
        }
        return ResponseEntity.ok(tokenResponse);
    }
}
