package com.or1is1.bartending.api.member;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public MemberJoinResponse join(@RequestBody MemberJoinRequest memberJoinRequest) {
        return memberService.join(memberJoinRequest);
    }
}
