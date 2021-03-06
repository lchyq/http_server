package com.http.server.httpServer_6_0.simple;

import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;
import com.http.server.httpServer_5_0.rely.Value;
import com.http.server.httpServer_5_0.simple.SimpleWapper;
import com.http.server.httpServer_5_0.simple.loader.Loader;
import com.http.server.httpServer_6_0.rely.Containter;
import com.http.server.httpServer_6_0.rely.Mapper;
import com.http.server.httpServer_7_0.SimpleStandardWapper;
import com.http.server.httpServer_7_0.filter.config.GlobalApplicationFilterConfig;
import com.http.server.httpserver_8_0.load.resource.ResourceDirContext;
import com.http.server.httpserver_8_0.load.standard.ApplicationContext;

import javax.naming.directory.DirContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * context  容器
 * 包含 多个wapper 容器实例
 * 包含基础阈、阈
 * @author lucheng28
 * @date 2020-07-31
 */
public class SimpleContext implements Containter {
    //加载器
    private Loader loader;
    //映射器
    private Mapper mapper;
    //管道
    private SimpleContextPipeline simpleContextPipeline;
    // servlet映射
    private Map<String,String> servletMapping;
    //wapper映射
    private Map<String, SimpleStandardWapper> wapperMapping;
    private GlobalApplicationFilterConfig globalApplicationFilterConfig;
    //servlet context
    private ApplicationContext context;
    private DirContext resource;

    public SimpleContext(){
        simpleContextPipeline = new SimpleContextPipeline(this);
        servletMapping = new HashMap<>();
        wapperMapping = new HashMap<>();
    }

    @Override
    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    @Override
    public Loader getLoader() {
        return loader;
    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Mapper getMapper() {
        return this.mapper;
    }

    //寻找子容器
    public SimpleStandardWapper map(HttpRequest httpRequest, HttpResponse httpResponse){
        return mapper.map(httpRequest,httpResponse);
    }

    //处理请求
    public void invoke(HttpRequest httpRequest,HttpResponse httpResponse) throws ClassNotFoundException, IOException, InstantiationException, ServletException, IllegalAccessException, InterruptedException {
        simpleContextPipeline.invoke(httpRequest,httpResponse);
    }

    @Override
    public void addServletMapping(String pattern, String servletName) {
        servletMapping.put(pattern,servletName);
    }

    @Override
    public String findServletMapping(String pattern) {
        String servletName = servletMapping.get(pattern);
        if(servletName != null){
            return servletName;
        }
        return null;
    }

    @Override
    public void addChild(String servletName, SimpleStandardWapper simpleWapper) {
        simpleWapper.setParent(this);
        wapperMapping.put(servletName,simpleWapper);
    }

    @Override
    public SimpleStandardWapper findChild(String servletName) {
        SimpleStandardWapper wapper = wapperMapping.get(servletName);
        if(wapper != null){
            return wapper;
        }
        return null;
    }

    @Override
    public void setFilterConfig(GlobalApplicationFilterConfig globalApplicationFilterConfig) {
        this.globalApplicationFilterConfig = globalApplicationFilterConfig;
    }

    @Override
    public GlobalApplicationFilterConfig getGlobalApplicationFilterConfig() {
        return this.globalApplicationFilterConfig;
    }

    @Override
    public ServletContext getServletContext() {
        if(context == null){
            context = new ApplicationContext();
        }
        return context.getContext();
    }

    @Override
    public DirContext getResource() {
        if(resource != null)
            return resource;
        resource = new ResourceDirContext();
        return resource;
    }

    public void addValue(Value value){
        simpleContextPipeline.addValue(value);
    }
}
