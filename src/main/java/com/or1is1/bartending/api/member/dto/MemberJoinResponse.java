package com.or1is1.bartending.api.member.dto;


import com.or1is1.bartending.api.member.Member;

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
