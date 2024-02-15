package com.or1is1.bartending.api.configuration;

import com.or1is1.bartending.api.filter.LogFilter;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.lang.Integer.MIN_VALUE;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {
	@Bean
	public FilterRegistrationBean<Filter> logFilter() {
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

		filterRegistrationBean.setFilter(new LogFilter());
		filterRegistrationBean.setOrder(MIN_VALUE);
		filterRegistrationBean.addUrlPatterns(("/*"));

		return filterRegistrationBean;
	}
}
