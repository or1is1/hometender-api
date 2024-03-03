package com.or1is1.hometender.api.domain.member.exception;

import com.or1is1.hometender.api.domain.member.dto.IsExistMemberRequest;
import com.or1is1.hometender.api.domain.member.dto.PostMemberRequest;
import lombok.Getter;

@Getter
public class MemberAlreadyExistsException extends RuntimeException {
	private final IsExistMemberRequest isExistMemberRequest;

	public MemberAlreadyExistsException(PostMemberRequest postMemberRequest) {
		this.isExistMemberRequest = new IsExistMemberRequest(postMemberRequest);
	}
}
