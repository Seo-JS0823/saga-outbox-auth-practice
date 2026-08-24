package com.msa.auth.application.service;

import org.springframework.stereotype.Component;

import com.msa.auth.domain.model.PhantomToken;
import com.msa.auth.domain.port.out.TokenPort;
import com.msa.auth.domain.port.out.TokenStoragePort;
import com.msa.auth.domain.response.TokenExchangeResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenService {

	private final TokenPort tokenPort;
	
	private final TokenStoragePort tokenStoragePort;
	
	public String createPhantomToken() {
		return tokenPort.createPhantomToken();
	}
	
	public void createLoginTokenSet(Long userId, String phantomToken) {
		String phantomTokenHash = tokenPort.hashPhantomToken(phantomToken);
		String accessToken = tokenPort.createAccessToken(userId);
		
		PhantomToken token = new PhantomToken(phantomTokenHash, accessToken, userId);
		
		// 토큰 정보 저장 key = PhantomToken , value = AccessToken
		tokenStoragePort.saveToken(token);
	}
	
	public TokenExchangeResponse exchangeAccessToken(String phantomToken) {
		PhantomToken tokenSet = tokenStoragePort.findAccessTokenByHash(phantomToken)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 토큰입니다."));
		
		return new TokenExchangeResponse(
				tokenSet.getAccessToken(),
				tokenSet.getUserId()
		);
	}
	
}
