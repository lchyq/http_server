package com.http.server.httpServer_6_0.simple;

import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;
import com.http.server.httpServer_5_0.simple.SimpleWapper;
import com.http.server.httpServer_6_0.rely.Containter;
import com.http.server.httpServer_6_0.rely.Mapper;

/**
 * 映射器
 * 映射器的作用是 根据servlet的路径找到具体的servlet 的name
 * 根据servlet name 找到具体的wapper实例
 * wapper 与 servlet是一一对应的
 * @author lucheng28
 * @date 2020-07-31
 * 实现自己的映射器 应该是实现tomcat提供的 mapper接口
 * 此处 自己实现 仅作学习使用
 */
public class SimpleMapper implements Mapper {
    private Containter containter;
    @Override
    public Containter getContainer() {
        return containter;
    }

    @Override
    public void setContainer(Containter container) {
        this.containter = container;
    }

    @Override
    public SimpleWapper map(HttpRequest httpRequest, HttpResponse httpResponse) {
//        String uri = httpRequest.getRequestURI();
        String uri = "/MyServlet";
        String requestPath = uri.substring(uri.indexOf("/"));

        String servletName = getContainer().findServletMapping(requestPath);
        SimpleContext simpleContext = (SimpleContext) getContainer();
        return simpleContext.findChild(servletName);
    }
}
