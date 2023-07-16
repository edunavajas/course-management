package com.garajeideas.coursemanagement.service;


import com.garajeideas.coursemanagement.openapi.web.rest.dtos.AuthenticationRequest;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
