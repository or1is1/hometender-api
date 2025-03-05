package com.or1is1.hometender.api.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    public Member(Long id) {
        this.id = id;
    }

    public Member(String loginId, String password, String nickname) {
        this.loginId = loginId;
        put(password, nickname);
    }

    public void put(String password, String nickname) {
        this.password = password;
        this.nickname = nickname;
    }
}
