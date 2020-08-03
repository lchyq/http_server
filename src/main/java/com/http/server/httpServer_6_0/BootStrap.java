package com.http.server.httpServer_6_0;

import com.http.server.httpServer_4_0.HttpConnector;
import com.http.server.httpServer_4_0.rely.LifecycleException;
import com.http.server.httpServer_5_0.simple.SimpleWapper;
import com.http.server.httpServer_5_0.simple.loader.SimpleLoader;
import com.http.server.httpServer_5_0.simple.value.SimpleValueOne;
import com.http.server.httpServer_5_0.simple.value.SimpleValueTwo;
import com.http.server.httpServer_6_0.simple.SimpleContext;
import com.http.server.httpServer_6_0.simple.SimpleMapper;
import com.http.server.httpServer_6_0.value.SImpleContextValueOne;
import com.http.server.httpServer_6_0.value.SImpleContextValueTwo;

import java.io.IOException;

/**
 * 启动类
 * @author lucheng
 */
public class BootStrap {
    public static void main(String[] args) throws IOException, LifecycleException {
        HttpConnector httpConnector = new HttpConnector();

        SimpleContext simpleContext = new SimpleContext();
        SimpleMapper simpleMapper = new SimpleMapper();
        simpleMapper.setContainer(simpleContext);
        simpleContext.setMapper(simpleMapper);

        SimpleLoader simpleLoader = new SimpleLoader();
        simpleContext.setLoader(simpleLoader);

        simpleContext.addServletMapping("/MyServlet","MyServlet");

        SimpleWapper simpleWapper = new SimpleWapper();
        simpleWapper.setServlet("com.http.server.httpserver2_0.servlet.MyServlet");
        SimpleValueOne simpleValueOne = new SimpleValueOne();
        SimpleValueTwo simpleValueTwo = new SimpleValueTwo();
        simpleWapper.addValue(simpleValueOne);
        simpleWapper.addValue(simpleValueTwo);

        simpleContext.addChild("MyServlet",simpleWapper);
        SImpleContextValueOne sImpleContextValueOne = new SImpleContextValueOne();
        SImpleContextValueTwo sImpleContextValueTwo = new SImpleContextValueTwo();
        simpleContext.addValue(sImpleContextValueOne);
        simpleContext.addValue(sImpleContextValueTwo);

        httpConnector.setContainer(simpleContext);
        httpConnector.init();
        httpConnector.connectorStart();

        System.in.read();
    }
}
