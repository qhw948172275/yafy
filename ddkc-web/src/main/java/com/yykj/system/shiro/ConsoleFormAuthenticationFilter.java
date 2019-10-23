package com.yykj.system.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleFormAuthenticationFilter extends FormAuthenticationFilter {

	private static final Logger log = LoggerFactory.getLogger(ConsoleFormAuthenticationFilter.class);
	/**
	 * 登录成功-跳转地址
	 */
	public ConsoleFormAuthenticationFilter() {
	}
	public ConsoleFormAuthenticationFilter(String loginSuccessUrl){
		setSuccessUrl(loginSuccessUrl);
	}
	
	
	
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
            	log.debug("3333333333333333333333");
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
            	log.debug("4444444444444444444444");
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
        	log.debug("5555555555555555555555555");
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }

            saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }
	
	
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken " +
                    "must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        }
        try {
        	log.debug("666666666666666");
            Subject subject = getSubject(request, response);
            log.debug("77777777777777777777");
            subject.login(token);
            log.debug("888888888888888888888");
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
        	log.debug("999999999999999999999");
            return onLoginFailure(token, e, request, response);
        }
    }


//	/**
//	 * 登录成功操作
//	 * 
//	 * <pre>
//	* 用户登录成功之后，记录用户登录次数，最后一次登录IP，最后一次登录时间
//	 * </pre>
//	 * 
//	 * @param token
//	 * @param subject
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginSuccess(org.apache.shiro.authc.AuthenticationToken,
//	 *      org.apache.shiro.subject.Subject, javax.servlet.ServletRequest,
//	 *      javax.servlet.ServletResponse)
//	 */
//	@Override
//	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest req,
//			ServletResponse response) throws Exception {
//		/* 记录登录信息 */
//		//setSuccessUrl(this.loginSuccessUrl);
//		System.out.println("10101010101010101010101011010101001010101010");
//		log.debug("10101010100101010101010");
//		SavedRequest request = WebUtils.getAndClearSavedRequest(req);
//		if(null != request){
//			if(request.getMethod().equals(AccessControlFilter.GET_METHOD) && null != request.getRequestUrl()){
//				((HttpServletResponse)response).sendRedirect(getSuccessUrl()+"?redirectUrl="+URLEncoder.encode(request.getRequestUrl(), SystemConstants.DEFAULT_CHARSET));
//				return false;
//			}
//		}
//		issueSuccessRedirect(req, response);
//        return false;
//	}
}
