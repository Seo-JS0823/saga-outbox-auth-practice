package com.msa.auth.domain.request;

public final class UserCommandRequest {

	public record SignupRequest(String email, String password, String nickname) {}
	
	public record LoginRequest(String email, String password) {}
	
	public record UserUpdateRequest(String password, String nickname) {}
	
}
