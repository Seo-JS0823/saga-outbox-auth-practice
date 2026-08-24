package com.msa.auth.adapter.web.cookie;

import java.time.Duration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CookieProperty {

	TOKEN("User-Authorization", true, Duration.ofMinutes(30)),
	
	
	;
	
	private final String name;
	
	private final boolean httpOnly;
	
	private final Duration maxAge;
}
