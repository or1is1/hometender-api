package com.or1is1.hometender.api;


public record ErrorResponse(
		ErrorCode code,
		String message
) {
}
