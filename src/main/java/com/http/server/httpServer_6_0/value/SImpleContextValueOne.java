package com.http.server.httpServer_6_0.value;

import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;
import com.http.server.httpServer_5_0.rely.Value;
import com.http.server.httpServer_5_0.rely.ValueContext;

import javax.servlet.ServletException;
import java.io.IOException;
/**
 * context 阈
 * @author lucheng28
 * @date 2020-07-31
 */
public class SImpleContextValueOne implements Value {
    @Override
    public void invokeNext(HttpRequest httpRequest, HttpResponse httpResponse, ValueContext valueContext) throws ClassNotFoundException, ServletException, IOException, IllegalAccessException, InstantiationException {
        System.out.println("阈处理-1");
        valueContext.invokeNext(httpRequest,httpResponse);
    }
}
