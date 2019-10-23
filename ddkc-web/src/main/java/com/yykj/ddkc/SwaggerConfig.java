package com.yykj.ddkc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置类
 */
@Configuration
//@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket createRestApiForParent() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).groupName("会员端接口").select()
				.apis(RequestHandlerSelectors.basePackage("com.yykj.ddkc.api.controller.member"))
				.paths(PathSelectors.any()).build().securitySchemes(securitySchemes("memberToken"))
				.securityContexts(securityContextsByMember());
	}

	@Bean
	public Docket createRestApiForTeacher() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).groupName("商家端接口").select()
				.apis(RequestHandlerSelectors.basePackage("com.yykj.ddkc.api.controller.shop"))
				.paths(PathSelectors.any()).build().securitySchemes(securitySchemes("shopToken"))
				.securityContexts(securityContextsByShop());
//                .globalOperationParameters(pars());
	}
	
	@Bean
	public Docket createRestApiForCommons() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).groupName("公用接口").select()
				.apis(RequestHandlerSelectors.basePackage("com.yykj.ddkc.api.controller.commons"))
				.paths(PathSelectors.any()).build();
//                .globalOperationParameters(pars());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("叮咚快超接口设计").version("1.0")
				.description("《叮咚快超》项目是一款LBS地理位置定位的社区综合服务平台。基于微信小程序进行开发。本文档主要是针对小程序端的会员、商户两种角色设计对应的服务端接口。").build();
	}

	private List<ApiKey> securitySchemes(String token) {
		List<ApiKey> list = new ArrayList<ApiKey>();
		ApiKey ak = new ApiKey(token, token, "header");
		list.add(ak);
		return list;
	}

	private List<SecurityContext> securityContextsByShop() {
		List<SecurityContext> list = new ArrayList<SecurityContext>();
		SecurityContext sc = SecurityContext.builder().securityReferences(defaultAuth("shopToken"))
				.forPaths(PathSelectors.regex("^(?!auth).*$")).build();
		list.add(sc);
		return list;
	}

	private List<SecurityContext> securityContextsByMember() {
		List<SecurityContext> list = new ArrayList<SecurityContext>();
		SecurityContext sc = SecurityContext.builder().securityReferences(defaultAuth("memberToken"))
				.forPaths(PathSelectors.regex("^(?!auth).*$")).build();
		list.add(sc);
		return list;
	}

	/**
	 * 根据接口不同动态往header当中添加不同的token名称对应的token值信息
	 *
	 * @author chenbiao
	 * @date 2019年8月13日 上午10:22:35 参数说明
	 *
	 * @param tokenName
	 * @return
	 */
	List<SecurityReference> defaultAuth(String tokenName) {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		List<SecurityReference> list = new ArrayList<SecurityReference>();
		SecurityReference sr = new SecurityReference(tokenName, authorizationScopes);
		list.add(sr);
		return list;
	}

	/**
	 * 全局添加token信息，由于接口分商户端和会员端，所以废弃掉
	* @author chenbiao
	* @date 2019年8月13日 上午10:23:33
	* 参数说明
	*
	* @return
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private List<Parameter> pars() {
		ParameterBuilder ticketPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<Parameter>();
		ticketPar.name("token").description("token").modelRef(new ModelRef("string")).parameterType("header")
				.required(false).build(); // header中的token参数非必填，传空也可以
		pars.add(ticketPar.build());

		return pars;
	}

}
