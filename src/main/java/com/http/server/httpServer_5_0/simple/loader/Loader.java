package com.http.server.httpServer_5_0.simple.loader;

/**
 * loader接口
 * 模拟tomcat中的Loader接口
 * @author lucheng28
 * @date 2020-07-28
 */
public interface Loader {

    ClassLoader getClassLoader();

    Object load(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException;
}
