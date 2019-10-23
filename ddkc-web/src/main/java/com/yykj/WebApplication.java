package com.yykj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 叮咚开超项目
 * 
 * @author chenbiao
 * @date 2018年7月28日 下午3:57:19
 * 
 *       开启任务调度 @EnableScheduling
 *
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
@ComponentScan("com.yykj")
public class WebApplication{

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

}
