package com.or1is1.bartending.api.member.exception;

import com.or1is1.bartending.api.member.dto.MemberExistsRequest;
import com.or1is1.bartending.api.member.dto.MemberJoinRequest;
import lombok.Getter;

@Getter
public class MemberAlreadyExistsException extends RuntimeException {
	private final MemberExistsRequest memberExistsRequest;

	public MemberAlreadyExistsException(MemberJoinRequest memberJoinRequest, String message) {
		super(message);
		this.memberExistsRequest = new MemberExistsRequest(memberJoinRequest);
	}
}
