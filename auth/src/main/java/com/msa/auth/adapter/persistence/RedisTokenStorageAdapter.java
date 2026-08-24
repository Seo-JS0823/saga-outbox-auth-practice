package com.msa.auth.adapter.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.msa.auth.domain.model.PhantomToken;
import com.msa.auth.domain.port.out.TokenPort;
import com.msa.auth.domain.port.out.TokenStoragePort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisTokenStorageAdapter implements TokenStoragePort {
	
	private final RedisTokenRepository tokenRepository;
	
	private final TokenPort tokenPort;
	
	@Override
	public void saveToken(PhantomToken phantomToken) {
		tokenRepository.save(phantomToken);
	}
	
	@Override
	public Optional<PhantomToken> findAccessTokenByHash(String phantomToken) {
		if(phantomToken == null || phantomToken.isBlank()) {
			return Optional.empty();
		}
		
		String phantomTokenHash = tokenPort.hashPhantomToken(phantomToken);
		
		return tokenRepository.findById(phantomTokenHash);
	}

	@Override
	public void deleteToken(String phantomToken) {
		String phantomTokenHash = tokenPort.hashPhantomToken(phantomToken);
		
		tokenRepository.deleteById(phantomTokenHash);
	}

}
