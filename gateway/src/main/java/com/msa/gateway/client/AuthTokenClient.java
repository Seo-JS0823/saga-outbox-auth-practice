package com.msa.gateway.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthTokenClient {

	private final WebClient.Builder webClientBuilder;
	
	@Value("${app.auth.gateway-secret}")
	private String gatewaySecret;
	
	public Mono<TokenExchangeResponse> exchange(String phantomToken) {
		return webClientBuilder.build()
				.get()
				.uri("http://auth/internal/tokens/exchange")
				.header("X-Phantom-Token", phantomToken)
				.header("X-Gateway-Secret", gatewaySecret)
				.retrieve()
				.bodyToMono(TokenExchangeResponse.class)
				.onErrorMap(error -> new InvalidPhantomTokenException());
	}
	
	public record TokenExchangeResponse(String accessToken, Long userId) {}
}
