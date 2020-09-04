package com.http.server.httpServer_7_0.strap;

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
import com.http.server.httpServer_7_0.FilterDef;
import com.http.server.httpServer_7_0.SimpleStandardWapper;
import com.http.server.httpServer_7_0.filter.FilterOne;
import com.http.server.httpServer_7_0.filter.FilterTwo;
import com.http.server.httpServer_7_0.filter.config.ApplicationFilterConfig;
import com.http.server.httpServer_7_0.filter.config.GlobalApplicationFilterConfig;

import java.io.IOException;

/**
 * 启动类
 * @author lucheng28
 * @date 2020-09-03
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

        SimpleStandardWapper simpleWapper = new SimpleStandardWapper();
        simpleWapper.setServletClass("com.http.server.httpserver2_0.servlet.MyServlet");
        simpleWapper.setServletName("MyServlet");
        SimpleValueOne simpleValueOne = new SimpleValueOne();
        SimpleValueTwo simpleValueTwo = new SimpleValueTwo();
        simpleWapper.addValue(simpleValueOne);
        simpleWapper.addValue(simpleValueTwo);
        simpleWapper.setSTM(true);

        simpleContext.addServletMapping("/MyServlet",simpleWapper.getServletName());

        simpleContext.addChild(simpleWapper.getServletName(),simpleWapper);
        SImpleContextValueOne sImpleContextValueOne = new SImpleContextValueOne();
        SImpleContextValueTwo sImpleContextValueTwo = new SImpleContextValueTwo();
        simpleContext.addValue(sImpleContextValueOne);
        simpleContext.addValue(sImpleContextValueTwo);

        GlobalApplicationFilterConfig globalApplicationFilterConfig = new GlobalApplicationFilterConfig();
        FilterDef filterDef1 = new FilterDef();
        filterDef1.setFilterName(FilterOne.class.getSimpleName());
        filterDef1.setFilterClass(FilterOne.class.getName());

        FilterDef filterDef2 = new FilterDef();
        filterDef2.setFilterName(FilterTwo.class.getSimpleName());
        filterDef2.setFilterClass(FilterTwo.class.getName());

        globalApplicationFilterConfig.addFilterConfig(new ApplicationFilterConfig(simpleContext,filterDef1));
        globalApplicationFilterConfig.addFilterConfig(new ApplicationFilterConfig(simpleContext,filterDef2));
        simpleContext.setFilterConfig(globalApplicationFilterConfig);

        httpConnector.setContainer(simpleContext);
        httpConnector.init();
        httpConnector.connectorStart();

        System.in.read();
    }
}
