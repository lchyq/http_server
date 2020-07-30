package com.http.server.httpServer_4_0;

import com.http.server.httpServer_4_0.rely.LifecycleException;
import com.http.server.httpServer_5_0.simple.SimpleWapper;

import java.io.IOException;

/**
 * httpServer启动类
 * @author lucheng28
 * @date 2020-07-26
 * 第四版与第三版的主要区别在于
 * 抽象出了默认连接器，container
 * 连接器与container容器是一对一的关系
 * container容器主要用来处理servlet请求
 * connector 用来接受请求，并将请求交给对应的httpProcessor处理
 * httpProcessor 采用线程与池化处理方式，避免频繁创建
 */
public class BootStrap {
    public static void main(String[] args) throws LifecycleException, IOException {
        HttpConnector httpConnector = new HttpConnector();
        SimpleWapper defaultContainer = new SimpleWapper();
        httpConnector.setContainer(defaultContainer);

        httpConnector.init();
        httpConnector.connectorStart();

        System.in.read();
    }
}
