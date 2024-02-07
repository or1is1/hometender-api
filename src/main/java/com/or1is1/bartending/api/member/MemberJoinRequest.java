package com.or1is1.bartending.api.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberJoinRequest(
        @NotBlank(message = "공백은 허용되지 않습니다.")
        @Size(min = 5, max = 20, message = "아이디는 5자 이상, 20자 미만이여야 합니다.")
        String loginId,

        @NotBlank(message = "공백은 허용되지 않습니다.")
        @Size(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 미만이여야 합니다.")
        String password,

        @NotBlank(message = "공백은 허용되지 않습니다.")
        @Size(min = 2, max = 10, message = "닉네임은 2자 이상, 10자 미만이여야 합니다.")
        String nickname
) {
}
