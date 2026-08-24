package com.msa.auth.adapter.web.cookie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieProvider {

	private final boolean secure;
	
	public CookieProvider(@Value("${app.auth.cookie.secure}") boolean secure) {
		this.secure = secure;
	}
	
	public void addCookie(CookieProperty cookieProperty, String value, HttpServletResponse response) {
		response.addHeader(HttpHeaders.SET_COOKIE, createCookie(cookieProperty, value));
	}
	
	private String createCookie(CookieProperty cookieProperty, String value) {
		ResponseCookie responseCookie = ResponseCookie.from(cookieProperty.getName(), value)
				.path("/")
				.httpOnly(cookieProperty.isHttpOnly())
				.secure(secure)
				.maxAge(cookieProperty.getMaxAge())
				.sameSite("strict")
				.build();
		
		return responseCookie.toString();
	}
	
}
