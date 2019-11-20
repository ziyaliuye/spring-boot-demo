package cn.cnm.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lele
 * @version 1.0
 * @Description 自定义Servlet组件
 * @Email 414955507@qq.com
 * @date 2019/11/20 17:19
 */
public class MyServlet extends HttpServlet {
    // 处理get请求
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 调用doPost()处理
        doPost(req, resp);
    }

    // 处理post请求
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Hello MyServlet...");
    }
}
