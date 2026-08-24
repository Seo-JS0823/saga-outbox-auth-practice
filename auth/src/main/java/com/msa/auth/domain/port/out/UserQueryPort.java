package com.msa.auth.domain.port.out;

import java.util.Optional;

import com.msa.auth.domain.model.User;
import com.msa.auth.domain.request.UserCommandRequest.LoginRequest;

public interface UserQueryPort {

	boolean checkEmail(String email);
	
	boolean checkNickname(String nickname);
	
	boolean validateLogin(User user, LoginRequest loginRequest);
	
	Optional<User> findByEmail(String email);
}
