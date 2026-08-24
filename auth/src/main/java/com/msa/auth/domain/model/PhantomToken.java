package com.msa.auth.domain.model;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@RedisHash(value = "phantom", timeToLive = 1800)
@NoArgsConstructor(force = true)
public class PhantomToken {

	@Id
	private final String phantomTokenHash;
	
	private final String accessToken;
	
	private final Long userId;
	
	@PersistenceCreator
	public PhantomToken(String phantomTokenHash, String accessToken, Long userId) {
		this.phantomTokenHash = Objects.requireNonNull(phantomTokenHash);
		this.accessToken = Objects.requireNonNull(accessToken);
		this.userId = Objects.requireNonNull(userId);
	}
	
}
