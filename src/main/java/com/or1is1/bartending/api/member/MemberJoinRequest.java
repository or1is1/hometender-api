package com.or1is1.bartending.api.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberJoinRequest(
        @NotBlank
        @Size(min = 5, max = 20, message = "{validation.constraints.Size.loginId}")
        String loginId,

        @NotBlank
        @Size(min = 8, max = 16, message = "{validation.constraints.Size.password}")
        String password,

        @NotBlank
        @Size(min = 2, max = 10, message = "{validation.constraints.Size.nickname}")
        String nickname
) {
}
