package com.or1is1.bartending.api.member;

import jakarta.annotation.Nonnull;
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
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Nonnull
    private String password;

    @Nonnull
    private String nickname;

    public Member(@Nonnull String email, @Nonnull String password, @Nonnull String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
