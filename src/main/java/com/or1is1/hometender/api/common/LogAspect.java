package com.or1is1.hometender.api.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LogAspect {

	@Pointcut("within(com.or1is1.hometender.api.domain..*)") // 패키지 범위 설정
	public void domain() {
	}

	@Before("domain()")
	public void beforeRequest(JoinPoint joinPoint) {
		log.info("###Start request {}", joinPoint.getSignature().toShortString());
		Arrays.stream(joinPoint.getArgs())
				.map(Object::toString)
				.map(str -> "\t" + str)
				.forEach(log::info);
	}

	@AfterReturning(pointcut = "domain()", returning = "returnValue")
	public void afterReturningLogging(JoinPoint joinPoint, Object returnValue) {
		log.info("###End request {}", joinPoint.getSignature().toShortString());

		if (returnValue == null) return;

		log.info("\t{}", returnValue.toString());
	}

	@AfterThrowing(pointcut = "domain()", throwing = "e")
	public void afterThrowingLogging(JoinPoint joinPoint, Exception e) {
		log.error("###Occured error in request {}", joinPoint.getSignature().toShortString());
		log.error("\t{}", e.getMessage());
	}
}
