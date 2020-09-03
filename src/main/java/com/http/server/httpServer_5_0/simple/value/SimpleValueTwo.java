package com.http.server.httpServer_5_0.simple.value;

import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;
import com.http.server.httpServer_5_0.rely.Value;
import com.http.server.httpServer_5_0.rely.ValueContext;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 阈
 * @author lucheng28
 * @date 2020-07-28
 * 基础阈 的前置拦截器
 */
public class SimpleValueTwo implements Value {
    @Override
    public void invokeNext(HttpRequest httpRequest, HttpResponse httpResponse, ValueContext valueContext) throws ServletException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, InterruptedException {
        System.out.println("前置阈-2-处理");
        valueContext.invokeNext(httpRequest,httpResponse);
    }
}
