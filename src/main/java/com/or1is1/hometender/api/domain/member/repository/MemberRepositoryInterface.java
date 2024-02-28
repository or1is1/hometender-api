package com.or1is1.hometender.api.domain.member.repository;

import com.or1is1.hometender.api.domain.member.dto.response.MemberIsExistsResponse;

public interface MemberRepositoryInterface {
	public MemberIsExistsResponse isExists(String loginId, String nickname);
}
