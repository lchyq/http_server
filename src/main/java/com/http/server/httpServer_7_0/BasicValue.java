package com.http.server.httpServer_7_0;

import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;
import com.http.server.httpServer_5_0.rely.Value;
import com.http.server.httpServer_5_0.rely.ValueContext;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 基础阈
 * @author lucheng28
 * @date 2020-09-01
 */
public class BasicValue implements Value {
    private SimplePipeline simplePipeline;
    public BasicValue (SimplePipeline simplePipeline){
        this.simplePipeline = simplePipeline;
    }
    @Override
    public void invokeNext(HttpRequest httpRequest, HttpResponse httpResponse, ValueContext valueContext) throws ClassNotFoundException, ServletException, IOException, IllegalAccessException, InstantiationException {

    }
}
