package com.msa.auth.domain.result;

public final class UserResult {

	public record LoginResult(String phantomToken) {}
}
