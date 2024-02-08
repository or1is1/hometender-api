package com.or1is1.bartending.api.member;

public record ExceptionResponse(
		String field,
		String code,
		String message
) {
}
