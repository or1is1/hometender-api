package com.or1is1.hometender.api.domain.member;

import com.or1is1.hometender.api.dto.IsExistMemberResponse;

public interface MemberRepositoryInterface {
	IsExistMemberResponse isExists(String loginId, String nickname);
}
