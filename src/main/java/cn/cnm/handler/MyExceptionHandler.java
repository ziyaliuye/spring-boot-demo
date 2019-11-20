package cn.cnm.handler;

import cn.cnm.exception.UserNotExistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lele
 * @version 1.0
 * @Description
 * @Email 414955507@qq.com
 * @date 2019/11/20 13:09
 */
/* SpringMVC中自定义异常的处理需要需要注解@ControllerAdvice */
@ControllerAdvice
public class MyExceptionHandler {
    // 处理对应的异常， 需要在方法上注解@ExceptionHandler， 对应具体的异常类
    @ExceptionHandler(UserNotExistException.class)
    /* 将返回的对象自动转为Json串 */
    @ResponseBody
    public Map<String, Object> myHandlerException(Exception e) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "no user...");
        map.put("message", e.getMessage());
        return map;
    }
}
