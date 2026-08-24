package com.msa.auth.global;

import java.time.Instant;

public interface Clock {

	Instant now();
	
	Instant nowSecond();
}
