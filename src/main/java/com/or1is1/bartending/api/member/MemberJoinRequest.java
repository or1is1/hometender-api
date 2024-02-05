package com.or1is1.bartending.api.member;

public record MemberJoinRequest(
        String email,

        String password,

        String nickname
) {
}
