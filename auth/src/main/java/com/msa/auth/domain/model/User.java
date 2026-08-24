package com.msa.auth.domain.model;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.msa.auth.domain.request.UserCommandRequest.SignupRequest;
import com.msa.auth.domain.request.UserCommandRequest.UserUpdateRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "nickname", nullable = false, length = 10)
	private String nickname;
	
	public static User create(SignupRequest signupRequest, PasswordEncoder passwordEncoder) {
		User user = new User();
		
		user.email = signupRequest.email();
		user.nickname = signupRequest.nickname();
		user.password = passwordEncoder.encode(signupRequest.password());
		
		return user;
	}
	
	// 비밀번호 변경
	public void changePassword(UserUpdateRequest userUpdateRequest, PasswordEncoder passwordEncoder) {
		if(isDuplicatePassword(userUpdateRequest.password(), passwordEncoder)) {
			throw new IllegalArgumentException("기존 비밀번호와 동일합니다.");
		}
		
		this.password = passwordEncoder.encode(userUpdateRequest.password());
	}
	
	// 닉네임 변경
	public void changeNickname(UserUpdateRequest userUpdateRequest) {
		this.nickname = userUpdateRequest.nickname();
	}
	
	// 기존 비밀번호와 같은지 확인
	public boolean isDuplicatePassword(String password, PasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(password, this.password);
	}
	
}
