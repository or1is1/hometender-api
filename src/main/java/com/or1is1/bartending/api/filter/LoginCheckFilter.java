package com.or1is1.bartending.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.or1is1.bartending.api.CommonResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;
import java.util.Locale;

import static com.or1is1.bartending.api.StringConst.LOGIN_MEMBER;
import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class LoginCheckFilter implements Filter {
	private static final String[] whitelist = {"/", "/api/members/join", "/api/members/login", "/css/*"};
	private final MessageSource messageSource;
	private ObjectMapper objectMapper;

	@Override
	public void init(FilterConfig filterConfig) {
		objectMapper = new ObjectMapper();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		if (!isAuthenticated(httpServletRequest)) {
			String message = messageSource.getMessage("member.exception.notAuthenticated", null, Locale.KOREAN);
			CommonResponse<Void> objectCommonResponse = new CommonResponse<>(message, null);

			log.info("{} | sessionIsNull = {}", message, httpServletRequest.getSession(false) == null);

			String content = objectMapper.writeValueAsString(objectCommonResponse);
			httpServletResponse.setContentType(APPLICATION_JSON_VALUE);
			httpServletResponse.setCharacterEncoding(UTF_8.name());
			httpServletResponse.setStatus(SC_BAD_REQUEST);
			httpServletResponse.getWriter().write(content);

			return;
		}

		chain.doFilter(request, response);
	}

	private boolean isAuthenticated(HttpServletRequest httpServletRequest) {
		String requestURI = httpServletRequest.getRequestURI();

		if (PatternMatchUtils.simpleMatch(whitelist, requestURI)) {
			return true;
		}

		HttpSession session = httpServletRequest.getSession(false);

		return session != null && session.getAttribute(LOGIN_MEMBER) != null;
	}
}
