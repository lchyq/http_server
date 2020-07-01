package com.http.server.httpserver1_0;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;

/**
 * 简单的http服务器
 * @author lucheng28
 * @date 2020-06-29
 */
public class HttpServer {
    private static final String SHUTDOWN = "/SHUTDOWN";
    private Boolean shutDown = false;

    private void await() throws Exception {
        ServerSocket serverSocket = new ServerSocket(8080,1,InetAddress.getByName("127.0.0.1"));

        while (!shutDown){
            Socket socket = serverSocket.accept();
            InputStream stream = socket.getInputStream();
            Request request = new Request(stream);

            Response response = new Response(socket.getOutputStream());
            response.setStream(request);
            String uri = response.sendUri();
            System.out.println(String.format("uri:{%s}", uri));
            response.sendStaticSource();
        }
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = new HttpServer();
        server.await();

    }
}
