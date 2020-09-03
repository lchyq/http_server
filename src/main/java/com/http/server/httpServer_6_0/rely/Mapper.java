package com.http.server.httpServer_6_0.rely;

import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;
import com.http.server.httpServer_7_0.SimpleStandardWapper;

/**
 * Mapper接口
 * 该接口应该是由 tomcat提供
 * 此处仅仅知识模拟 做学习使用
 */
public interface Mapper {
    Containter getContainer();
    void setContainer(Containter container);
    SimpleStandardWapper map(HttpRequest httpRequest, HttpResponse httpResponse);
}
