package com.http.server.httpServer_5_0.simple.value;

import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;
import com.http.server.httpServer_5_0.rely.Value;
import com.http.server.httpServer_5_0.rely.ValueContext;
import com.http.server.httpServer_5_0.simple.SimplePipeline;
import com.http.server.httpServer_5_0.simple.SimpleWapper;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 基础阈
 * @author lucheng28
 * @date 2020-07-28
 * pipeline 会最后 调用 基础阈 来 处理请求
 * 这里的处理请求是调用容器中的servlet来对请求进行处理
 */
public class BasicValue implements Value {
    private SimplePipeline pipeline;
    public BasicValue(SimplePipeline pipeline){
        this.pipeline = pipeline;
    }
    @Override
    public void invokeNext(HttpRequest httpRequest, HttpResponse httpResponse, ValueContext valueContext) throws ClassNotFoundException, ServletException, IOException, IllegalAccessException, InstantiationException {
        SimpleWapper simpleWapper = pipeline.getContainer();
        Servlet servlet = simpleWapper.allocate();
        servlet.service(httpRequest,httpResponse);
    }
}
