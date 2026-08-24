package com.msa.auth.domain.response;

import com.msa.auth.domain.model.User;

public final class UserCommandResponse {

	public record SignupResponse(Long userId, String email, String nickname) {
		public static final SignupResponse from(User user) {
			return new SignupResponse(
					user.getUserId(),
					user.getEmail(),
					user.getNickname()
			);
		}
	}
	
}
