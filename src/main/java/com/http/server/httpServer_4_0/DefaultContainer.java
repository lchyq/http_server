package com.http.server.httpServer_4_0;

import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lucheng28
 * @date 2020-07-26
 * 若是扩展 tomcat的container容器，则需要实现 tomcat提供的container接口
 * 此处不实现，仅作学习使用
 * container的作用是用来处理servlet请求的
 * 与httpConnector是一对一的关系
 */
public class DefaultContainer {
    private static final String serlvetRepostory = "D:\\httpserver\\src\\main\\java\\com\\http\\server\\httpserver2_0\\servlet";
    private static Map<String,String> servletMap = new HashMap<>();
    private static Map<String,String> servletAddressMapping = new HashMap<>();
    private static URLClassLoader urlClassLoader;
    public void invoke(HttpRequest request, HttpResponse response) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, ServletException {
//        String uri = request.getRequestURI();
        String uri = "/MyServlet";
        URL[] urls = new URL[1];
        String path = uri.substring(uri.indexOf("/"));
//        String servletName = servletMap.get(path);
        String servletName = "MyServlet";
//        String servletClazz = servletAddressMapping.get(servletName);
        String servletClazz = "com.http.server.httpserver2_0.servlet.MyServlet";
        File file = new File(serlvetRepostory);
        String rep = new URL("file",null,file.getCanonicalPath() + "\\").toString();

        urls[0] = new URL(rep);
        urlClassLoader = new URLClassLoader(urls);
        Class c = urlClassLoader.loadClass(servletClazz);
        Servlet servlet = (Servlet) c.newInstance();
        servlet.service(request,response);


    }
}
