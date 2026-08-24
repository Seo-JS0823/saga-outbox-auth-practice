package com.msa.auth.domain.response;

public record TokenExchangeResponse(
		String accessToken,
		Long userId
) {

}
