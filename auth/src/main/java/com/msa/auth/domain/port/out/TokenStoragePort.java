package com.msa.auth.domain.port.out;

import java.util.Optional;

import com.msa.auth.domain.model.PhantomToken;

public interface TokenStoragePort {

	void saveToken(PhantomToken phantomToken);
	
	Optional<PhantomToken> findAccessTokenByHash(String phantomToken);
	
	void deleteToken(String phantomToken);
}
