package cn.cnm.component;

import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author lele
 * @version 1.0
 * @Description 可以在连接上携带区域信息
 * @Email 414955507@qq.com
 * @date 2019/11/19 21:18
 */
public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        // 从request中获取参数， 看是否有国际化的参数
        String i18n = request.getParameter("i18n");
        System.out.println("=============i18n");
        // 如果没有内容则使用默认的Locale
        Locale locale = Locale.getDefault();
        if (StringUtils.isEmpty(i18n)) {
            String[] split = i18n.split("_");
            locale = new Locale(split[0], split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
