package com.yykj.ddkc.api.controller.commons;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.yykj.ddkc.api.annotation.MemberLoginInterceptor;
import com.yykj.ddkc.api.annotation.ShopLoginInterceptor;

/**
 * 添加拦截器
 * 
 * @author chenbiao
 * @date 2018年9月18日 下午10:03:58
 *
 */
@Configuration
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
		registry.addInterceptor(getShopLoginInterceptor()).addPathPatterns("/api/shop/**");
		registry.addInterceptor(getMemberLoginInterceptor()).addPathPatterns("/api/member/**");
	}

	@Bean
	public HandlerInterceptor getShopLoginInterceptor() {
		return new ShopLoginInterceptor();
	}

	@Bean
	public HandlerInterceptor getMemberLoginInterceptor() {
		return new MemberLoginInterceptor();
	}

	/**
	 * 重新swagger指定静态资源
	 *
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/public/**").addResourceLocations("/public/");
		registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

	}

	/**
	 * 配置servlet处理
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * 重定义视图解析
	 * 
	 * @param registry
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#configureViewResolvers(org.springframework.web.servlet.config.annotation.ViewResolverRegistry)
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		/** 设置视图路径的前缀 */
		resolver.setPrefix("/WEB-INF/jsp/");
		/** 设置视图路径的后缀 */
		resolver.setSuffix(".jsp");
		registry.viewResolver(resolver);
		org.springframework.web.servlet.config.annotation.WebMvcConfigurer.super.configureViewResolvers(registry);
	}
}
