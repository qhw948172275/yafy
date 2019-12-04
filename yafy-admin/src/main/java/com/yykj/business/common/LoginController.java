package com.yykj.business.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yykj.system.commons.ip.IPUtils;
import com.yykj.system.dto.LoginUserDto;
import com.yykj.system.service.LoginLogService;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.JsonUtils;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import com.yykj.system.entity.SysLoginLog;
import com.yykj.system.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "登录管理")
@RestController
public class LoginController {


    @Autowired
    LoginLogService loginLogService;

    /**
     * 登录
     * @return
     */
    @ApiOperation(value = "登录")
  @PostMapping(value = "login")
  public JsonResult login(@RequestBody LoginUserDto loginUserDto, HttpServletRequest request){
        ObjectNode json= JsonUtils.getMapperInstance().createObjectNode();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginUserDto.getUsername(), loginUserDto.getPassword());
        try {
            subject.login(token);
            json.putPOJO("token", subject.getSession().getId());
            json.put("msg", "登录成功");
            SysUser sysUser=(SysUser)subject.getPrincipal();
            new LoginLogThread(loginLogService,getIpAddr(request),sysUser).start();
            //记录登陆日志
            return JsonResultUtils.buildJsonOK(json);
        } catch (IncorrectCredentialsException e) {
            json.put("msg", "密码错误");
        } catch (AuthenticationException e) {
            json.put("msg", "该用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonResultUtils.buildJsonFail(json);
    }

//    /**
//     * 刷新token
//     * @return
//     */
//    @GetMapping(value = "refreshToken")
//  public JsonResult refreshToken(){
//        try {
//            ObjectNode json= JsonUtils.getMapperInstance().createObjectNode();
//            Subject subject = SecurityUtils.getSubject();
//            subject.getSession().stop();
//            System.out.println(JsonUtils.beanToJson(subject.getPrincipal()));
//            subject.logout();
//            UsernamePasswordToken token = new UsernamePasswordToken("tengfei", "123456");
//            subject.login(token);
//            json.putPOJO("token",subject.getSession().getId());
//            return JsonResultUtils.buildJsonOK(json);
//        }catch (Exception e){
//            e.printStackTrace();
//            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
//        }
//  }

    /**
     * token失效
     * @return
     */
    @GetMapping(value = "unauth")
    public JsonResult unauth() {
        ObjectNode json = JsonUtils.getMapperInstance().createObjectNode();
        json.putPOJO("code", "10001");
        json.putPOJO("msg", "登录失效");
        return JsonResultUtils.buildJsonFail(json);
    }


    /**
     * 账户退出
     *
     * @return
     */
    @ApiOperation(value = "用户退出接口")
    @GetMapping(value = "loginOut")
    public JsonResult loginOut() {
        SecurityUtils.getSubject().logout();
        return JsonResultUtils.buildJson("退出成功！");
    }

    private static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

}
