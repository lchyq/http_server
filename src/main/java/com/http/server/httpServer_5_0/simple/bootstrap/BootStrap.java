package com.http.server.httpServer_5_0.simple.bootstrap;

import com.http.server.httpServer_4_0.HttpConnector;
import com.http.server.httpServer_4_0.rely.LifecycleException;
import com.http.server.httpServer_5_0.rely.Value;
import com.http.server.httpServer_5_0.simple.SimpleWapper;
import com.http.server.httpServer_5_0.simple.loader.SimpleLoader;
import com.http.server.httpServer_5_0.simple.value.SimpleValueOne;
import com.http.server.httpServer_5_0.simple.value.SimpleValueTwo;

import java.io.IOException;

/**
 * 启动类
 * @author lucheng28
 * @date 2020-07-30
 */
public class BootStrap {
    public static void main(String[] args) throws IOException, LifecycleException {
        HttpConnector httpConnector = new HttpConnector();

        SimpleWapper container = new SimpleWapper();
        Value value1 = new SimpleValueOne();
        Value value2 = new SimpleValueTwo();
        container.addValue(value1);
        container.addValue(value2);
        //wapper代表的是一个独立的servlet 因此需要设置关联的servlet
        //tomcat中应该是使用 配置文件的方式进行填充
        container.setServlet("MyServlet");
        container.setLoader(new SimpleLoader());

        httpConnector.setContainer(container);
        httpConnector.init();
        httpConnector.connectorStart();

        System.in.read();

    }
}
