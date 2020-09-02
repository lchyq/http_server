package com.http.server.httpServer_7_0.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author lucheng28
 * @date 2020-09-02
 * 模拟servlet中的过滤器配置
 */
public class FilterOne implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器初始化..");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("开始过滤");
    }

    @Override
    public void destroy() {
        System.out.println("过滤器销毁");
    }
}
