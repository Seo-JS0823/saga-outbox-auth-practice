package com.msa.auth.application.service;

import org.springframework.stereotype.Service;

import com.msa.auth.domain.model.User;
import com.msa.auth.domain.port.out.UserCommandPort;
import com.msa.auth.domain.port.out.UserQueryPort;
import com.msa.auth.domain.request.UserCommandRequest.SignupRequest;
import com.msa.auth.domain.response.UserCommandResponse.SignupResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCommandService {

	private final UserCommandPort userCommandPort;
	
	private final UserQueryPort userQueryPort;
	
	public SignupResponse signup(SignupRequest signupRequest) {
		if(userQueryPort.checkEmail(signupRequest.email())) {
			throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
		}
		
		if(userQueryPort.checkNickname(signupRequest.nickname())) {
			throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
		}
		
		User user = userCommandPort.save(signupRequest);
		
		return SignupResponse.from(user);
	}
	
}
