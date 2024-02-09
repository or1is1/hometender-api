package com.or1is1.bartending.api;

public record BindExceptionResponse(
		String field,
		String code,
		String message
) {
}
