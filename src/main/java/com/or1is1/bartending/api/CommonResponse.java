package com.or1is1.bartending.api;


import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public record CommonResponse<T>(
		@JsonInclude(NON_NULL)
		String message,
		@JsonInclude(NON_NULL)
		T data
) {
}
