package com.or1is1.hometender.api.domain.member.exception;

import com.or1is1.hometender.api.domain.member.dto.IsExistMemberRequest;
import com.or1is1.hometender.api.domain.member.dto.PostMemberRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

@Getter
@NoArgsConstructor(access = PRIVATE, force = true)
@RequiredArgsConstructor(access = PUBLIC)
public class MemberAlreadyExistsException extends RuntimeException {

	private final IsExistMemberRequest isExistMemberRequest;

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

	public MemberAlreadyExistsException(PostMemberRequest postMemberRequest) {
		this.isExistMemberRequest = new IsExistMemberRequest(postMemberRequest);
	}
}
