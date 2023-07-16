package com.garajeideas.coursemanagement.service.impl;

import com.garajeideas.coursemanagement.openapi.web.rest.dtos.AuthenticationRequest;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.AuthenticationResponse;
import com.garajeideas.coursemanagement.security.jwt.TokenProvider;
import com.garajeideas.coursemanagement.service.AuthService;
import com.garajeideas.coursemanagement.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.createToken(authentication);

            return new AuthenticationResponse(jwt);
        } catch (AuthenticationException e) {
            log.error("Failed to authenticate user: {}", request.getUsername(), e);
            throw e;
        }
    }
}
