package com.http.server.httpServer_7_0;

import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;
import com.http.server.httpServer_5_0.rely.Value;
import com.http.server.httpServer_5_0.rely.ValueContext;
import com.http.server.httpServer_6_0.simple.SimpleContext;
import com.http.server.httpServer_7_0.filter.chain.ApplicationFilterChain;
import com.http.server.httpServer_7_0.filter.config.GlobalApplicationFilterConfig;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 基础阈
 * @author lucheng28
 * @date 2020-09-01
 */
public class SimpleWapperBasicValue implements Value {
    private SimplePipeline simplePipeline;
    public SimpleWapperBasicValue(SimplePipeline simplePipeline){
        this.simplePipeline = simplePipeline;
    }
    @Override
    public void invokeNext(HttpRequest httpRequest, HttpResponse httpResponse, ValueContext valueContext) throws ClassNotFoundException, ServletException, IOException, IllegalAccessException, InstantiationException, InterruptedException {
        SimpleStandardWapper simpleStandardWapper = (SimpleStandardWapper)simplePipeline.getContainter();
        Servlet servlet = simpleStandardWapper.allocate();
        ApplicationFilterChain applicationFilterChain = createFilterChain();
        applicationFilterChain.setServlet(servlet);
        applicationFilterChain.doFilter(httpRequest,httpResponse);
        valueContext.invokeNext(httpRequest,httpResponse);

    }

    private ApplicationFilterChain createFilterChain(){
        SimpleStandardWapper simpleStandardWapper = (SimpleStandardWapper)simplePipeline.getContainter();
        SimpleContext simpleContext = (SimpleContext) simpleStandardWapper.getParent();
        GlobalApplicationFilterConfig globalApplicationFilterConfig = simpleContext.getGlobalApplicationFilterConfig();
        return new ApplicationFilterChain().initFilters(globalApplicationFilterConfig);
    }
}
