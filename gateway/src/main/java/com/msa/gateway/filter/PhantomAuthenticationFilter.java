package com.msa.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.msa.gateway.client.AuthTokenClient;
import com.msa.gateway.client.InvalidPhantomTokenException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PhantomAuthenticationFilter implements GlobalFilter, Ordered {
	
	private static final String COOKIE_NAME = "User-Authorization";
	
	private static final String USER_ID_ATTRIBUTE = "authenticatedUserId";
	
	private final AuthTokenClient authTokenClient;
	
	@Override
	public int getOrder() {
		return -1;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		
		if(isPublicRequest(request)) {
			return chain.filter(exchange);
		}
		
		HttpCookie cookie = request.getCookies().getFirst(COOKIE_NAME);
		
		if(cookie == null || cookie.getValue().isBlank()) {
			return unauthorized(exchange);
		}
		
		return authTokenClient.exchange(cookie.getValue())
				.flatMap(token -> {
					exchange.getAttributes().put(USER_ID_ATTRIBUTE, token.userId());
					
					ServerHttpRequest mutatedRequest = request.mutate()
							.headers(headers -> {
								headers.remove(HttpHeaders.AUTHORIZATION);
								
								headers.setBearerAuth(token.accessToken());
							})
							.build();
					
					return chain.filter(exchange
							.mutate()
							.request(mutatedRequest)
							.build()
					);
				})
				.onErrorResume(
						InvalidPhantomTokenException.class,
						error -> unauthorized(exchange)
				);
	}

	private Mono<Void> unauthorized(ServerWebExchange exchange) {
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		return exchange.getResponse().setComplete();
	}

	private boolean isPublicRequest(ServerHttpRequest request) {
		String path = request.getURI().getPath();
		HttpMethod method = request.getMethod();
		
		return (method == HttpMethod.POST && path.equals("/api/v1/users/signup"))
				|| (method == HttpMethod.POST && path.equals("/api/v1/users/login"))
				|| (method == HttpMethod.GET &&  path.equals("/actuator/health"));
	}

	
}
