package com.edunavajas.coursemanagement.rest;

import com.edunavajas.coursemanagement.openapi.web.rest.AuthApi;
import com.edunavajas.coursemanagement.openapi.web.rest.dtos.AuthenticationRequest;
import com.edunavajas.coursemanagement.openapi.web.rest.dtos.AuthenticationResponse;
import com.edunavajas.coursemanagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController implements AuthApi {

    private final AuthService authenticationService;

    @Override
    public ResponseEntity<AuthenticationResponse> authenticateUser(AuthenticationRequest authenticationRequest) {
        try {
            AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
