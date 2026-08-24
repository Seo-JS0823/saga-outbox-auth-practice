package com.msa.auth.application.usecase;

import org.springframework.stereotype.Service;

import com.msa.auth.application.service.TokenService;
import com.msa.auth.application.service.UserQueryService;
import com.msa.auth.domain.model.User;
import com.msa.auth.domain.request.UserCommandRequest.LoginRequest;
import com.msa.auth.domain.result.UserResult.LoginResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

	private final UserQueryService userQueryService;
	
	private final TokenService tokenService;
	
	public LoginResult login(LoginRequest loginRequest) {
		User user = userQueryService.loginUser(loginRequest);
		
		String rawPhantomToken = tokenService.createPhantomToken();
		
		tokenService.createLoginTokenSet(user.getUserId(), rawPhantomToken);
		
		return new LoginResult(rawPhantomToken);
	}
	
}
