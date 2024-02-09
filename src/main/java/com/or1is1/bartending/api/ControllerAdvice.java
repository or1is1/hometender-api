package com.or1is1.bartending.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.or1is1.bartending.api.member")
@Slf4j
public class ControllerAdvice {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BindExceptionResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {

		BindingResult bindingResult = ex.getBindingResult();
		String field = bindingResult.getFieldError().getField();
		ObjectError objectError = bindingResult.getAllErrors().get(0);
		String message = objectError.getDefaultMessage();
		String code = objectError.getCode();

		log.error(message, ex);

		return new BindExceptionResponse(field, code, message);
	}
}
