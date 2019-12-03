package com.yykj.business.common;

import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author qhw
 * @Date 2019/12/3 0003 下午 18:15
 * @Version V1.0
 **/
@RestControllerAdvice
public class CustomExceptionHandler {
    public static final Logger log= LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = RuntimeException.class)
    public JsonResult handlerException(Exception e, HttpServletRequest request){
            log.debug("\nurt:{},\nmsg:{}",request.getRequestURL(),e.getMessage());
        return JsonResultUtils.buildJsonFailMsg(e.getMessage());
    }
}
