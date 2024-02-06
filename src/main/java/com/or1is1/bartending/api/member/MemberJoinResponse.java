package com.or1is1.bartending.api.member;


public record MemberJoinResponse(
        Long id,
        String nickname
) {
    public MemberJoinResponse(Member member) {
        this(
                member.getId(),
                member.getNickname()
        );
    }
}
