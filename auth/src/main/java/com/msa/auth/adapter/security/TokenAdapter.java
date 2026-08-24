package com.msa.auth.adapter.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HexFormat;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.msa.auth.domain.port.out.TokenPort;
import com.msa.auth.global.Clock;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenAdapter implements TokenPort {

	private static final int PHANTOM_TOKEN_LENGTH = 64;
	
	private static final String PHANTOM_TOKEN_MATERIAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	
	private final SecureRandom secureRandom = new SecureRandom();
	
	private final Duration accessExpiration;
	
	private final SecretKey key;
	
	private final Clock clock;
	
	public TokenAdapter(
			@Value("${jwt.access.expiration}") Duration accessExpiration,
			@Value("${jwt.secret}") String secretKey,
			Clock clock
	) {
		
		this.accessExpiration = accessExpiration;
		this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
		this.clock = clock;
	}
	
	@Override
	public String createPhantomToken() {
		StringBuilder token = new StringBuilder(PHANTOM_TOKEN_LENGTH);
		for(int i = 0; i < PHANTOM_TOKEN_LENGTH; i++) {
			int index = secureRandom.nextInt(PHANTOM_TOKEN_MATERIAL.length());
			token.append(PHANTOM_TOKEN_MATERIAL.charAt(index));
		}
		return token.toString();
	}

	@Override
	public String hashPhantomToken(String phantomToken) {
		try {
			byte[] hash = MessageDigest.getInstance("SHA-256")
					.digest(phantomToken.getBytes(StandardCharsets.UTF_8));
			
			return HexFormat.of().formatHex(hash);
		} catch (NoSuchAlgorithmException e) {
			log.error("[Auth Service - TokenAdapter]: 토큰 해시를 만들 알고리즘이 존재하지 않습니다.", e);
			
			throw new IllegalStateException("SHA-256을 사용할 수 없습니다.");
		}
	}
	
	@Override
	public String createAccessToken(Long userId) {
		Instant now = clock.nowSecond();
		Date iss = Date.from(now);
		Date exp = Date.from(now.plus(accessExpiration));
		
		String accessToken = Jwts.builder()
				.subject(userId.toString())
				.issuedAt(iss)
				.expiration(exp)
				.signWith(key)
				.compact();
		
		return accessToken;
	}

}
