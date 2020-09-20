package com.http.server.httpserver_8_0.load.standard;

import com.http.server.httpserver_8_0.load.Loader;
import com.http.server.httpserver_8_0.load.WebAppClassLoader;
import com.http.server.httpserver_8_0.load.resource.ResourceEntry;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;

/**
 * 应用类加载器 tomcat中有此实现
 * @author lucheng28
 * @date 2020-09-08
 *
 */
public class WebAppLoader implements Loader {
    //内部依赖的类加载器
    private WebAppClassLoader webAppClassLoader;
    //默认实现得类加载器
    private static final String defClassLoaderString = "com.http.server.httpserver_8_0.load.WebAppClassLoader";
    //用户自定义类加载器
    private String classLoaderString = null;
    //应用级别资源缓存
    private HashMap<String, ResourceEntry> resourceMap = new HashMap<>();
    private ByteArrayOutputStream bos = new ByteArrayOutputStream();

    @Override
    public void setClassLoaderString(String classLoaderString) {
        if (StringUtils.isNotEmpty(classLoaderString)) {
            this.classLoaderString = classLoaderString;
        }
    }

    @Override
    public WebAppClassLoader getWebAppClassLoader() throws IllegalAccessException, InstantiationException,
            ClassNotFoundException, IOException, NoSuchMethodException, InvocationTargetException {

        ObjectOutputStream oos = null;
        if (webAppClassLoader != null) {
            return webAppClassLoader;
        }
        if (StringUtils.isNotEmpty(classLoaderString)) {
            //检查本地缓存是否缓存该类
            Class webAppClassLoaderClass = findLoadedResource(classLoaderString);
            if (webAppClassLoaderClass != null) {
                return (WebAppClassLoader) webAppClassLoaderClass.newInstance();
            }

            //利用系统加载器加载用户自定义类
            webAppClassLoaderClass = Class.forName(classLoaderString);
            if (webAppClassLoaderClass != null) {
                oos = new ObjectOutputStream(bos);
                oos.writeObject(webAppClassLoaderClass);
                ResourceEntry resourceEntry = new ResourceEntry();
                resourceEntry.setResourceName(webAppClassLoaderClass.getName());
                resourceEntry.setResourceClass(webAppClassLoaderClass);
                resourceEntry.setResourceNameByte(webAppClassLoaderClass.getName().getBytes());
                resourceEntry.setResourceClassByte(bos.toByteArray());
                Date lastModified = new Date();
                resourceEntry.setLastModified(lastModified);
                resourceEntry.setLastModifiedTime(lastModified.getTime());
                resourceMap.put(classLoaderString, resourceEntry);
                oos.close();
                bos.close();
                return (WebAppClassLoader) webAppClassLoaderClass.newInstance();
            } else {
                throw new ClassNotFoundException();
            }
        }

        Class clazz = Class.forName(defClassLoaderString);
        return (WebAppClassLoader) clazz.newInstance();
    }

    @Override
    public boolean delegate() {
        return webAppClassLoader.delegate();
    }

    @Override
    public void setDelegate(boolean delegate) {
        webAppClassLoader.setDelegate(delegate);
    }

    @Override
    public Class load(String name) throws ClassNotFoundException {
        return webAppClassLoader.loadClass0(name);
    }

    @Override
    public void setReload(boolean reload) {
        webAppClassLoader.reload(reload);
        //todo 设置重载的同时 就需要启动线程来扫描资源了
    }

    @Override
    public Boolean reload() {
        return webAppClassLoader.getLoaded();
    }

    //获取已经缓存的资源
    private Class findLoadedResource(String resourceName) {
        ResourceEntry resourceEntry = resourceMap.get(resourceName);
        if (resourceEntry != null) {
            return resourceEntry.getResourceClass();
        }
        return null;
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        WebAppLoader webAppLoader = new WebAppLoader();
        WebAppClassLoader webAppClassLoader = webAppLoader.getWebAppClassLoader();
        Class c = webAppClassLoader.loadClass0("com.http.server.httpserver2_0.servlet.MyServlet");
        System.out.println(c.getSimpleName());
    }
}
