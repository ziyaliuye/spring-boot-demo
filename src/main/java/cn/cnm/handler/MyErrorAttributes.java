package cn.cnm.handler;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * @author lele
 * @version 1.0
 * @Description 自定义ErrorAttributes, 方便起见继承DefaultErrorAttributes， 只需要重写 方法即可
 * 作用是可以携带自定义的数据
 * @Email 414955507@qq.com
 * @date 2019/11/20 14:16
 */
/* 确保能被Spring扫描到 */
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {
//    @Override
//    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
//        Map<String, Object> map = super.getErrorAttributes(webRequest, includeStackTrace);
//        /* 自定义携带信息 */
//        map.put("company", "wocao");
//        map.put("company1", "wocao");
//        // 从Request冲获取属性， 第二个参数是范围， 0表Request
//        Map<String, Object> ext = (Map<String, Object>) webRequest.getAttribute("ext", 0);
//        //map.put("ext", ext);
//        return map;
//    }


    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, includeStackTrace);
        map.put("company", "atguigu");
        return map;
    }
}
