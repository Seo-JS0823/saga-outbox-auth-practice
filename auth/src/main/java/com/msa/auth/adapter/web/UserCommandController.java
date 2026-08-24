package com.msa.auth.adapter.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msa.auth.adapter.web.cookie.CookieProperty;
import com.msa.auth.adapter.web.cookie.CookieProvider;
import com.msa.auth.application.service.UserCommandService;
import com.msa.auth.application.usecase.LoginUseCase;
import com.msa.auth.domain.request.UserCommandRequest.LoginRequest;
import com.msa.auth.domain.request.UserCommandRequest.SignupRequest;
import com.msa.auth.domain.response.UserCommandResponse.SignupResponse;
import com.msa.auth.domain.result.UserResult.LoginResult;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserCommandController {

	private final CookieProvider cookieProvider;
	
	private final UserCommandService userCommandService;
	
	private final LoginUseCase loginUseCase;
	
	@PostMapping("/signup")
	public ResponseEntity<SignupResponse> signup(
			@RequestBody SignupRequest signupRequest
	) {
		
		SignupResponse response = userCommandService.signup(signupRequest);
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Void> login(
			@RequestBody LoginRequest loginRequest,
			HttpServletResponse servletResponse
	) {
		LoginResult result = loginUseCase.login(loginRequest);
		
		cookieProvider.addCookie(
				CookieProperty.TOKEN,
				result.phantomToken(),
				servletResponse
		);
		
		return ResponseEntity
				.noContent()
				.build();
	}
	
	
}
