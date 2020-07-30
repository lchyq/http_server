package com.http.server.httpServer_5_0.rely;

import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 阈上下文
 * 用来遍历阈的
 * tomcat中会提供 该接口
 * @author lucheng28
 * @date 2020-07-26
 */
public interface ValueContext {
    void invokeNext(HttpRequest httpRequest, HttpResponse httpResponse) throws ServletException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException;
}
