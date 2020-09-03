package com.http.server.httpServer_5_0.simple.loader;

import com.http.server.httpServer_5_0.simple.SimpleWapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * tomcat 中实现自己的 loader 需要实现tomcat中的loader接口
 * @author lucheng28
 * @date 2020-07-28
 */
public class SimpleLoader implements Loader {
    private ClassLoader classLoader;
    private static final String servletRoot = "D:\\httpserver\\src\\main\\java\\com\\http\\server\\httpserver2_0\\servlet";
    private static final String filterRoot = "D:\\httpserver\\src\\main\\java\\com\\http\\server\\httpServer_7_0\\filter";
    public SimpleLoader() throws IOException {
        URL[] urls = new URL[2];
        File servletFile = new File(servletRoot);
        File filterFile = new File(filterRoot);
        String servletRep = new URL("file",null,servletFile.getCanonicalPath() + "\\").toString();
        String filterRep = new URL("file",null,filterFile.getCanonicalPath() + "\\").toString();
        urls[0] = new URL(servletRep);
        urls[1] = new URL(filterRep);
        classLoader = new URLClassLoader(urls);
    }
    @Override
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    @Override
    public Object load(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //根据name获取到servlet的全限定名
        //此处应该使用配置的方式，此处省略
//        name = "com.http.server.httpserver2_0.servlet.MyServlet";
        Class clazz = classLoader.loadClass(name);
        return clazz.newInstance();
    }
}
