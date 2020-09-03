package com.http.server.httpServer_6_0.rely;

import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;
import com.http.server.httpServer_5_0.simple.SimpleWapper;
import com.http.server.httpServer_5_0.simple.loader.Loader;
import com.http.server.httpServer_7_0.SimpleStandardWapper;
import com.http.server.httpServer_7_0.filter.config.GlobalApplicationFilterConfig;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * container接口
 * 该接口应该是由 tomcat提供
 * 此处仅仅只是模拟 做学习使用
 */
public interface Containter {
    void setLoader(Loader loader);
    Loader getLoader();
    void setMapper(Mapper mapper);
    Mapper getMapper();
    void invoke(HttpRequest httpRequest, HttpResponse httpResponse) throws ClassNotFoundException, IOException, InstantiationException, ServletException, IllegalAccessException, InterruptedException;
    void addServletMapping(String pattren,String servletName);
    String findServletMapping(String pattern);
    //此处应该添加的是 container 而不是 wapper
    //此处做简化处理
    void addChild(String servletName, SimpleStandardWapper containter);
    SimpleStandardWapper findChild(String servletName);
    //TODO 设置全局filter 配置,咱不了解tomcat是如何实现的
    void setFilterConfig(GlobalApplicationFilterConfig globalApplicationFilterConfig);
    GlobalApplicationFilterConfig getGlobalApplicationFilterConfig();

}
