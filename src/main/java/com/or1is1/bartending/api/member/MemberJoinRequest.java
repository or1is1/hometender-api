package com.or1is1.bartending.api.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberJoinRequest(
        @Email(message = "잘못된 이메일 형식입니다.")
        String email,

        @NotBlank(message = "공백은 허용되지 않습니다.")
        @Size(min = 4, max = 20, message = "비밀번호는 4자 이상, 20자 미만이여야 합니다.")
        String password,

        @NotBlank(message = "공백은 허용되지 않습니다.")
        @Size(min = 2, max = 10, message = "닉네임은 2자 이상, 10자 미만이여야 합니다.")
        String nickname
) {
}
