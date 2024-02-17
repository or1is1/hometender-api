package com.or1is1.hometender.api.member.repository;

import com.or1is1.hometender.api.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryInterface {
	Optional<Member> findByLoginId(String loginId);
}
