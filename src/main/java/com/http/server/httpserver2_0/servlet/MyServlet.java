package com.http.server.httpserver2_0.servlet;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class MyServlet implements Servlet {
    private static final String header = "HTTP/1.1 200 ok\r\n" + "Content-Type: text/html\r\n" + "Content-Length: 13\r\n" +"\r\n";
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //当调用某个servlet时才会被初始化
        //只会被初始化一次
        System.out.println("servlet init");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("start servlet service");
        PrintWriter writer = servletResponse.getWriter();
        writer.write(header);
        writer.write("hello servlet");
        writer.flush();
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        System.out.println("servlet destroy");
    }
}
