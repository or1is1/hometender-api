package com.or1is1.hometender.api.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static lombok.AccessLevel.PROTECTED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice(basePackages = "com.or1is1.hometender.api")
@RequiredArgsConstructor(access = PROTECTED)
@Slf4j
public class GlobalExceptionHandler {

	private final MessageSource messageSource;

	@ExceptionHandler
	@ResponseStatus(INTERNAL_SERVER_ERROR)
	public Exception methodArgumentNotValidExceptionHandler(Exception ex) {
		log.error("전역 예외 핸들 : ", ex);

		return ex;
	}
}
