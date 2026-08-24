package com.msa.auth.adapter.persistence;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.msa.auth.domain.model.User;
import com.msa.auth.domain.port.out.UserCommandPort;
import com.msa.auth.domain.port.out.UserQueryPort;
import com.msa.auth.domain.request.UserCommandRequest.LoginRequest;
import com.msa.auth.domain.request.UserCommandRequest.SignupRequest;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserCommandPort, UserQueryPort {

	private final JpaUserRepository userRepository;

	private final PasswordEncoder passwordEncoder;
	
	@Override
	public User save(SignupRequest signupRequest) {
		User user = User.create(signupRequest, passwordEncoder);
		
		return userRepository.save(user);
	}

	@Override
	public boolean checkEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean checkNickname(String nickname) {
		return userRepository.existsByNickname(nickname);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public boolean validateLogin(User user, LoginRequest loginRequest) {
		return user.isDuplicatePassword(loginRequest.password(), passwordEncoder);
	}
	
	
}
