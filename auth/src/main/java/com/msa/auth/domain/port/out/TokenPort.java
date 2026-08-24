package com.msa.auth.domain.port.out;

public interface TokenPort {
	String createPhantomToken();
	
	String hashPhantomToken(String phantomToken);
	
	String createAccessToken(Long userId);
}
