package com.edunavajas.coursemanagement.service;


import com.edunavajas.coursemanagement.openapi.web.rest.dtos.AuthenticationRequest;
import com.edunavajas.coursemanagement.openapi.web.rest.dtos.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
