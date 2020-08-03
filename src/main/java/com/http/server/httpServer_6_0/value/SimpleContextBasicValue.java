package com.http.server.httpServer_6_0.value;

import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;
import com.http.server.httpServer_5_0.rely.Value;
import com.http.server.httpServer_5_0.rely.ValueContext;
import com.http.server.httpServer_5_0.simple.SimplePipeline;
import com.http.server.httpServer_5_0.simple.SimpleWapper;
import com.http.server.httpServer_6_0.rely.Containter;
import com.http.server.httpServer_6_0.simple.SimpleContextPipeline;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * context 基础阈
 * @author lucheng28
 * @date 2020-07-31
 */
public class SimpleContextBasicValue implements Value {
    private SimpleContextPipeline simplePipeline;
    public SimpleContextBasicValue(SimpleContextPipeline simplePipeline){
        this.simplePipeline = simplePipeline;
    }
    @Override
    public void invokeNext(HttpRequest httpRequest, HttpResponse httpResponse, ValueContext valueContext) throws ClassNotFoundException, ServletException, IOException, IllegalAccessException, InstantiationException {
        //获取子容器
        SimpleWapper subContaintor = simplePipeline.getContainter().getMapper().map(httpRequest,httpResponse);
        if(subContaintor != null){
            subContaintor.invoke(httpRequest,httpResponse);
            valueContext.invokeNext(httpRequest,httpResponse);
            return;
        }
        throw new RuntimeException("未匹配到相关servlet实例");
    }
}
