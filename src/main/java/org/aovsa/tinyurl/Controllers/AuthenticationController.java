package org.aovsa.tinyurl.Controllers;

import lombok.RequiredArgsConstructor;
import org.aovsa.tinyurl.Requests.AuthenticationRequest;
import org.aovsa.tinyurl.Requests.RegisterRequest;
import org.aovsa.tinyurl.Responses.AuthenticationResponse;
import org.aovsa.tinyurl.Services.Authentication.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        return authenticationService.register(registerRequest);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest AuthenticationRequest) {
        return authenticationService.authenticate(AuthenticationRequest);
    }
}
