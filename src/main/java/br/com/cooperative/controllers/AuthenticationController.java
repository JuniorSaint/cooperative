package br.com.cooperative.controllers;

import br.com.cooperative.models.Response.AuthenticationResponse;
import br.com.cooperative.models.request.AuthenticationRequest;
import br.com.cooperative.services.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "*", maxAge = 60 * 60)
@RequestMapping("/v1/authentication")
@Tag(name = "Authentication User", description = "Manager to authenticate a user")
public class AuthenticationController {
    @Autowired
    private AuthenticationService service;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
