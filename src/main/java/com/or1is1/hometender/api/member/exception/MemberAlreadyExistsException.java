package com.or1is1.hometender.api.member.exception;

import com.or1is1.hometender.api.member.dto.request.MemberExistsRequest;
import com.or1is1.hometender.api.member.dto.request.MemberJoinRequest;
import lombok.Getter;

@Getter
public class MemberAlreadyExistsException extends RuntimeException {
	private final MemberExistsRequest memberExistsRequest;

	public MemberAlreadyExistsException(MemberJoinRequest memberJoinRequest) {
		this.memberExistsRequest = new MemberExistsRequest(memberJoinRequest);
	}
}
