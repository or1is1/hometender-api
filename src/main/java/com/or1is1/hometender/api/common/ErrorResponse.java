package com.or1is1.hometender.api.common;


public record ErrorResponse(
		ErrorCode code,
		String message
) {
}
