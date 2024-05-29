package org.aovsa.tinyurl.Services.Authentication;

import lombok.RequiredArgsConstructor;
import org.aovsa.tinyurl.Models.Role;
import org.aovsa.tinyurl.Models.UserModel;
import org.aovsa.tinyurl.Repository.UserRepository;
import org.aovsa.tinyurl.Requests.AuthenticationRequest;
import org.aovsa.tinyurl.Requests.RegisterRequest;
import org.aovsa.tinyurl.Responses.AuthenticationResponse;
import org.aovsa.tinyurl.Services.JWT.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<AuthenticationResponse> register(RegisterRequest registerRequest) {

        if (registerRequest.getEmail() == null || registerRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(null));
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(null));
        }

        UserModel userModel = UserModel.builder()
                .id(UUID.randomUUID().toString())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(userModel);
        String token = jwtService.generateToken(userModel);
        return ResponseEntity.ok(new AuthenticationResponse(token));

    }

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {

        if (authenticationRequest.getEmail() == null || authenticationRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(null));
        }

        UserModel userModel = userRepository.findByEmail(authenticationRequest.getEmail()).orElse(null);

        if (userModel == null || !passwordEncoder.matches(authenticationRequest.getPassword(), userModel.getPassword())) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(null));
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        String token = jwtService.generateToken(userModel);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
