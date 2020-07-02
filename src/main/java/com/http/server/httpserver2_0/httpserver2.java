package com.http.server.httpserver2_0;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * httpServer 2.0 版本
 * 支持处理servlet 和 静态资源
 * @author lucheng28
 * @date 2020-07-01
 */
public class httpserver2 {
    private static final Integer port = 8080;
    private static final Integer backLog = 1;
    private static final String bindAddress = "127.0.0.1";
    private ServerSocket serverSocket = null;
    private static final boolean shutDown = false;
    private static final String shutContainer = "/shutDown";
    /**
     * 主要作用是舰艇端口 接受http请求
     */
    public void await() throws Exception {
        serverSocket = new ServerSocket(port,backLog,InetAddress.getByName(bindAddress));

        while(!shutDown){
            Socket socket = serverSocket.accept();
            Request request = new Request(socket.getInputStream());
            request.parse();

            Response response = new Response(socket.getOutputStream());
            response.setRequest(request);

            String uri = request.getUri();
            if(uri.startsWith("/servlet")){
                //servlet容器处理
                ServletProcesser servletProcesser = new ServletProcesser();
                servletProcesser.xml();
                servletProcesser.process(request,response);
            }else{
                //静态资源处理器处理
                StaticResourceProcesser processer = new StaticResourceProcesser();
                processer.process(request,response);
            }

            if(request.getUri().equals(shutContainer)){
                System.exit(-1);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        httpserver2 httpserver2 = new httpserver2();
        httpserver2.await();
    }
}
