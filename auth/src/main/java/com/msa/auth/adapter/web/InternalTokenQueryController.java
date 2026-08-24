package com.msa.auth.adapter.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msa.auth.application.service.TokenService;
import com.msa.auth.domain.response.TokenExchangeResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/tokens")
public class InternalTokenQueryController {

	@Value("${app.auth.gateway-secret}")
	private String gatewaySecret;
	
	private final TokenService tokenService;
	
	@GetMapping("/exchange")
	public ResponseEntity<TokenExchangeResponse> exchangeAccessToken(
			@RequestHeader("X-Phantom-Token") String phantomToken,
			@RequestHeader("X-Gateway-Secret") String gatewaySecret
	) {
		if(!this.gatewaySecret.equals(gatewaySecret)) {
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.build();
		}
		
		TokenExchangeResponse response = tokenService.exchangeAccessToken(phantomToken);
		
		return ResponseEntity.ok(response);
	}
	
}
