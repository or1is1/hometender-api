package com.or1is1.bartending.api.member.repository;

import com.or1is1.bartending.api.member.dto.MemberExistsResult;

public interface MemberRepositoryInterface {
	public MemberExistsResult isExists(String loginId, String nickname);
}
