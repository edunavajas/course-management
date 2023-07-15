package com.garajeideas.coursemanagement.rest;

import com.garajeideas.coursemanagement.openapi.web.rest.AuthApi;
import com.garajeideas.coursemanagement.openapi.web.rest.dtos.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController implements AuthApi {


	@Override
	public ResponseEntity<AuthenticationResponse> authenticateUser(AuthenticationRequest authenticationRequest) {
		return null;
	}
}
