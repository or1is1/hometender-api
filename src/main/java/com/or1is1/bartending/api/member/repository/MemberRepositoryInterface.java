package com.or1is1.bartending.api.member.repository;

import com.or1is1.bartending.api.member.dto.MemberIsExistsResult;

public interface MemberRepositoryInterface {
	public MemberIsExistsResult isExists(String loginId, String nickname);
}
