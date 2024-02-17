package com.or1is1.hometender.api.member.repository;

import com.or1is1.hometender.api.member.dto.MemberIsExistsResult;

public interface MemberRepositoryInterface {
	public MemberIsExistsResult isExists(String loginId, String nickname);
}
