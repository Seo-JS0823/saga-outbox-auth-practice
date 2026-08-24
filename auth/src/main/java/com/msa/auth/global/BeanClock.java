package com.msa.auth.global;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

@Component
public class BeanClock implements Clock {

	@Override
	public Instant now() {
		return Instant.now();
	}

	@Override
	public Instant nowSecond() {
		return Instant.now().truncatedTo(ChronoUnit.SECONDS);
	}

}
