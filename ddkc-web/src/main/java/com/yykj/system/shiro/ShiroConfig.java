package com.yykj.system.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * shiro控制类
* @author chenbiao
* @date 2018年7月17日 上午2:33:45 
*
 */
@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shirFilter(org.apache.shiro.mgt.SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        shiroFilterFactoryBean.setLoginUrl("/system/login");
        shiroFilterFactoryBean.setSuccessUrl("/system/loginSuccess");
        // 设置无权限时跳转的 url;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //系统管理模块所有请求都需要认证授权
        filterChainDefinitionMap.put("/system/loginSuccess", "anon");
        filterChainDefinitionMap.put("/system/**", "authc");
        
        //api开头的请求不走shiro过滤
        filterChainDefinitionMap.put("/api/**", "anon");
        
        //不需要认证的请求无需拦截
        filterChainDefinitionMap.put("/**", "anon");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        
        /**
         * 定义过滤器
         */
        Map<String, Filter> filters = new LinkedHashMap<String,Filter>();
        filters.put("authc", new ConsoleFormAuthenticationFilter("/system/loginSuccess"));   
        
        shiroFilterFactoryBean.setFilters(filters);
        
        System.out.println("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public org.apache.shiro.mgt.SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionMode("http");
        securityManager.setSessionManager(getSessionManager());
        securityManager.setCacheManager(getCacheManager());
        // 设置realm.
        securityManager.setRealm(customRealm());
        return securityManager;
    }
    /**
     * 缓存管理器
    * @author chenbiao
    * @date 2018年7月22日 上午2:25:03
    * 参数说明 
    * 
    * @return
     */
    private CacheManager getCacheManager() {
		return new MemoryConstrainedCacheManager();
	}

	@Bean
    public SessionManager getSessionManager(){
    	DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
    	sessionManager.setSessionIdUrlRewritingEnabled(false);//去掉url后面带上的jsessionid
    	sessionManager.setSessionIdCookie(getCookie());
    	return sessionManager;
    }

    private Cookie getCookie() {
    	return new SimpleCookie("custom.session.id");
	}

	/**
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean
    public Realm customRealm() {
        ConsoleRealm realm = new ConsoleRealm();
        realm.setFieldName("username");
        realm.setIsCredentialsMatcher(true);
        realm.setHashAlgorithmName("MD5");
        realm.setIsSalt(true);
        return realm;
    }
    
}
