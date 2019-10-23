package com.yykj;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import org.springframework.web.servlet.view.InternalResourceViewResolver;
import tk.mybatis.spring.annotation.MapperScan;

@ServletComponentScan
@SpringBootApplication
@MapperScan(basePackages = { "com.yykj.ddkc.dao","com.yykj.system.dao" })
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
public class ServiceApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ServiceApplication.class);
		application.setBannerMode(Mode.OFF);
		application.run(args);
	}

	/**
	 * 显式指定文件上传解析对象
	 * @author chenbiao
	 * @date 2018年6月4日 下午7:01:45 参数说明
	 * 
	 * @return
	 */
	@Bean(name = "multipartResolver")
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setResolveLazily(true);// resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
		resolver.setMaxInMemorySize(40960);
		resolver.setMaxUploadSize(100 * 1024 * 1024);// 上传文件大小 50M 50*1024*1024
		return resolver;
	}


}
