package com.or1is1.hometender.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.or1is1.hometender.api.common.ErrorResponse;
import com.or1is1.hometender.api.common.StringConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

import static com.or1is1.hometender.api.common.ErrorCode.MEMBER_NEED_TO_LOGIN;
import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Locale.KOREAN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class LoginCheckFilter implements Filter {
	private static final String[] whitelist = {
			"/api/members",
			"/api/members/login",
			"/swagger-ui/*",
			"/v3/api-docs/hometender-api",
			"/v3/api-docs/swagger-config"
	};
	private final MessageSource messageSource;
	private ObjectMapper objectMapper;

	@Override
	public void init(FilterConfig filterConfig) {
		objectMapper = new ObjectMapper();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String requestURI = httpServletRequest.getRequestURI();
		HttpSession session = httpServletRequest.getSession(false);

		if (needToLogin(requestURI, session)) {
			String message = messageSource.getMessage("exception.member.needToLogin", null, KOREAN);

			log.info("{} | requestUri = {} | sessionIsNull = {}", message, requestURI, session == null);

			ErrorResponse objectErrorResponse = new ErrorResponse(MEMBER_NEED_TO_LOGIN, message);
			String content = objectMapper.writeValueAsString(objectErrorResponse);

			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			httpServletResponse.setContentType(APPLICATION_JSON_VALUE);
			httpServletResponse.setCharacterEncoding(UTF_8.name());
			httpServletResponse.setStatus(SC_BAD_REQUEST);
			httpServletResponse.getWriter().write(content);

			return;
		}

		chain.doFilter(request, response);
	}

	private boolean needToLogin(String requestURI, HttpSession session) {
		if (PatternMatchUtils.simpleMatch(whitelist, requestURI)) {
			return false;
		}

		return session == null || session.getAttribute(StringConst.LOGIN_MEMBER) == null;
	}
}
