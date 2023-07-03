package com.jpa.test.filter;

public interface jwtProperties {
	String SECRET = "테스터";
	int EXPIRATION_TIME = 864000000;//10일
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Authorization";
}
