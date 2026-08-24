package com.msa.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Mono;

@Configuration
public class KeyResolverConfig {

	@Bean
	public KeyResolver userKeyResolver() {
		return exchange -> {
			Long userId = exchange.getAttribute("authenticatedUserId");
			
			return Mono.justOrEmpty(userId)
					.map(String::valueOf);
		};
	}
}
