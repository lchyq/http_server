package com.http.server.httpserver2_0;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * servlet处理器
 * @author lucheng28
 * @date 2020-07-02
 */
public class ServletProcesser {
    //servlet仓库地址
    private static final String servletRoot = "D:\\httpserver\\src\\main\\java\\com\\http\\server\\httpserver2_0\\servlet";
    private String servletName = null;
    private static String uri = null;
    private static URLClassLoader urlClassLoader = null;
    private static Map<String,String> servletMap = new HashMap<>();
    private static Map<String,String> servletAddressMapping = new HashMap<>();
    public void process(Request request,Response response) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, ServletException {
        uri = request.getUri();
        String path = uri.substring(uri.lastIndexOf("/"));
        servletName = servletAddressMapping.get(path);
        String servletClass = servletMap.get(servletName);

        File file = new File(servletRoot);
        URL[] urls = new URL[1];
        String rep = new URL("file",null,file.getCanonicalPath() + File.separator).toString();
        urls[0] = new URL(rep);

        urlClassLoader = new URLClassLoader(urls);

        Class c = urlClassLoader.loadClass(servletClass);
        Servlet servlet = (Servlet) c.newInstance();
        servlet.service(request,response);
    }

    //模拟servlet的xml配置
    public void xml(){
        servletAddressMapping.put("/MyServlet","MyServlet");
        servletMap.put("MyServlet","com.http.server.httpserver2_0.servlet.MyServlet");
    }
}
