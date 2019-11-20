package cn.cnm.servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author lele
 * @version 1.0
 * @Description
 * @Email 414955507@qq.com
 * @date 2019/11/20 17:30
 */
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("MyFilter process.....");
        // 放行
        chain.doFilter(request, response);
    }
}
