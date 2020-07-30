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
    //此处应该使用tomcat中的 container 接口替代
    //此处省略，但是含义相同
    private SimpleWapper parsent;
    private static final String servletRoot = "D:\\httpserver\\src\\main\\java\\com\\http\\server\\httpserver2_0\\servlet";
    public SimpleLoader() throws IOException {
        URL[] urls = new URL[1];
        File file = new File(servletRoot);
        String rep = new URL("file",null,file.getCanonicalPath() + "\\").toString();
        urls[0] = new URL(rep);
        classLoader = new URLClassLoader(urls);
    }
    @Override
    public ClassLoader getClassLoader() {
        if(classLoader != null){
            return classLoader;
        }
        if(parsent != null){
            return parsent.getLoader();
        }
        return null;
    }

    @Override
    public Object load(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //根据name获取到servlet的全限定名
        //此处应该使用配置的方式，此处省略
        name = "com.http.server.httpserver2_0.servlet.MyServlet";
        Class clazz = classLoader.loadClass(name);
        return clazz.newInstance();
    }
}
