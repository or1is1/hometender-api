package com.or1is1.hometender.api.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.or1is1.hometender.api.common.StringConst.REQUEST_UUID;
import static java.util.UUID.randomUUID;
import static org.slf4j.MDC.*;

@Slf4j
public class LogFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
			put(REQUEST_UUID, randomUUID().toString());

			chain.doFilter(request, response);
		} finally {
			clear();
		}
	}
}
