package com.or1is1.bartending.api.member.repository;

import com.or1is1.bartending.api.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
