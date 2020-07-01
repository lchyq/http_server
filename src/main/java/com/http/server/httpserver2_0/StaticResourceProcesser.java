package com.http.server.httpserver2_0;

/**
 * 静态资源处理器
 * @author lucheng28
 * @date 2020-07-01
 */
public class StaticResourceProcesser {
    public void process(Request request,Response response) throws Exception{
        response.handlerStaticResource();
    }
}
