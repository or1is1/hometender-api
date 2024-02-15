package com.or1is1.bartending.api.configuration;

import com.or1is1.bartending.api.filter.LogFilter;
import com.or1is1.bartending.api.filter.LoginCheckFilter;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.lang.Integer.MIN_VALUE;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {
	private final MessageSource messageSource;

	@Bean
	public FilterRegistrationBean<Filter> logFilter() {
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new LogFilter());
		filterRegistrationBean.setOrder(MIN_VALUE);
		filterRegistrationBean.addUrlPatterns(("/*"));

		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean<Filter> loginCheckFilter() {
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new LoginCheckFilter(messageSource));
		filterRegistrationBean.setOrder(0);
		filterRegistrationBean.addUrlPatterns(("/*"));

		return filterRegistrationBean;
	}
}
