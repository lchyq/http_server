package com.http.server.httpServer_5_0.rely;

import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 阈
 * 表示一个 处理器
 * tomcat中 会提供自己的value接口，这里模拟实现
 * @author lucheng28
 * @date 2020-07-28
 */
public interface Value {
    void invokeNext(HttpRequest httpRequest, HttpResponse httpResponse,ValueContext valueContext) throws ClassNotFoundException, ServletException, IOException, IllegalAccessException, InstantiationException;
}
