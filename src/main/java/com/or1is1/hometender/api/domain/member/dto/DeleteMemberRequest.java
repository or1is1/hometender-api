package com.or1is1.hometender.api.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DeleteMemberRequest(
		@NotBlank
		@Size(min = 8, max = 16, message = "{validation.constraints.Size.password}")
		String password
) {
}
