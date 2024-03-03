package com.or1is1.hometender.api.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostMemberRequest(
        @NotBlank(message = "{validation.constraints.NotBlank}")
        @Size(min = 5, max = 20, message = "{validation.constraints.Size.loginId}")
        String loginId,

        @NotBlank(message = "{validation.constraints.NotBlank}")
        @Size(min = 8, max = 16, message = "{validation.constraints.Size.password}")
        String password,

        @NotBlank(message = "{validation.constraints.NotBlank}")
        @Size(min = 2, max = 10, message = "{validation.constraints.Size.nickname}")
        String nickname
) {
}
