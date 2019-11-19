package cn.cnm.component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lele
 * @version 1.0
 * @Description 对请求时的登录状态进行拦截
 * @Email 414955507@qq.com
 * @date 2019/11/19 21:58
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
    // 目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取Session中的用户
        Object user = request.getSession().getAttribute("loginUser");
        // 没有任何用户登录
        if (user == null) {
            // 进行拦截， 强制返回登录页面
            request.setAttribute("msg", "请先进行登录");
            request.getRequestDispatcher("/login.html").forward(request, response);
            return false;
        } else {
            // 放行
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
