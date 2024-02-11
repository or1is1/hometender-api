package com.or1is1.bartending.api.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberExistsRequest(
        @NotBlank
        @Size(min = 5, max = 20, message = "{validation.constraints.Size.loginId}")
        String loginId,

        @NotBlank
        @Size(min = 2, max = 10, message = "{validation.constraints.Size.nickname}")
        String nickname
) {
        public MemberExistsRequest(MemberJoinRequest memberJoinRequest) {
                this(memberJoinRequest.loginId(), memberJoinRequest.nickname());
        }
}
