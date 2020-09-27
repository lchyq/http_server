package com.http.server.httpserver_8_0.load;

import com.http.server.httpServer_6_0.rely.Containter;

import javax.naming.NamingException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author lucheng28
 * @date 2020-09-08
 * loader接口 实现servlet 热加载需要实现该接口
 * tomcat中有提供该接口
 */
public interface Loader {
    //是否设置servlet热加载
    void setReload(boolean reload);
    //返回是否需要热加载
    Boolean reload();
    //设置类加载全限定名
    void setClassLoaderString(String classLoaderString);
    //获取内部来加载器，在tmocat实现中自定义类加载必须是 WebAppClassLoader 子类
    //这里模拟实现
    WebAppClassLoader createWebAppClassLoader() throws IllegalAccessException, InstantiationException, ClassNotFoundException, IOException, NoSuchMethodException, InvocationTargetException, NamingException;
    //是否委托给父类加载器
    boolean delegate();
    //设置是否委托给父类加载器
    void setDelegate(boolean delegate);
    Class load(String name) throws ClassNotFoundException;
    String[] findRepositories();
    void addRepository(String path);
    void setContainr(Containter containr);
    Containter getContainer();
}
