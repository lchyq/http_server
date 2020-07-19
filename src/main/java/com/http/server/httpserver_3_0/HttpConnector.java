package com.http.server.httpserver_3_0;

import com.http.server.httpserver_3_0.http.HttpRequest;
import com.http.server.httpserver_3_0.stream.SocketInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 连接器
 * @author lucheng28
 * @date 2020-07-06
 */
public class HttpConnector implements Runnable {
    private static final Integer port =8080;
    private static final Integer backLog = 1;
    private static final String bindAdd = "127.0.0.1";
    private static final boolean shutDown = false;

    @Override
    public void run() {
        try{
            ServerSocket serverSocket = new ServerSocket(port,backLog,InetAddress.getByName(bindAdd));

            while(!shutDown){
                //将拿到的client 交给 httpProcessor处理
                Socket client = serverSocket.accept();
                HttpProcessor httpProcessor = new HttpProcessor(new HttpRequest());
                httpProcessor.process(new SocketInputStream(client.getInputStream(),2048),client.getOutputStream());
                //servlet处理方式 同 httpServer_2_0
                //静态资源得处理方式 同 httpServer_2_0
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void conn(){
        Thread Connector = new Thread(this);
        Connector.start();
    }
}
