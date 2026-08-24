package com.msa.auth.application.service;

import org.springframework.stereotype.Service;

import com.msa.auth.domain.model.User;
import com.msa.auth.domain.port.out.UserQueryPort;
import com.msa.auth.domain.request.UserCommandRequest.LoginRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserQueryService {

	private final UserQueryPort userQueryPort;
	
	public User loginUser(LoginRequest loginRequest) {
		User user = userQueryPort.findByEmail(loginRequest.email())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
		
		if(!userQueryPort.validateLogin(user, loginRequest)) {
			throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
		}
		
		return user;
	}
}
