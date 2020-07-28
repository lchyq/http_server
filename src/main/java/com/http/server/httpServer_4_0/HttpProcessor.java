package com.http.server.httpServer_4_0;

import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;
import com.http.server.httpserver_3_0.stream.HttpHeader;
import com.http.server.httpserver_3_0.stream.HttpRequestLine;
import com.http.server.httpserver_3_0.stream.SocketInputStream;

import javax.servlet.ServletException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * httpProcessor 负责处理 http请求
 * @author lucheng28
 * @date 2020-07-26
 */
public class HttpProcessor implements Runnable {
    private String threadName;
    private Integer port;
    //代表当前请求是否可以处理
    private boolean avaliable = false;
    private HttpConnector httpConnector;
    private Socket socket;
    //代理端口
    private Integer proxyPort;
    //将请求行 的信息 封装在里面
    private HttpRequestLine httpRequestLine = new HttpRequestLine();
    //将请求头的信息封装在里面
    private HttpHeader header = new HttpHeader();
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;
    private boolean stoped = false;

    public HttpProcessor(){}
    public HttpProcessor(String threadName,Integer port,HttpConnector httpConnector){
        this.threadName = threadName;
        this.port = port;
        this.httpConnector = httpConnector;
    }

    //等待socket 并将其处理
    @Override
    public void run() {
        //processor 主逻辑
        while(!stoped){
            try {
                Socket socket = await();
                process(socket);
                //回收入栈
                httpConnector.recycle(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void start(){
        Thread thread = new Thread(this,threadName);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * 负责接受请求
     * @param socket
     */
    public synchronized void assign(Socket socket) throws InterruptedException {
        if(avaliable){
            //表当前请求可用，但是processor还没有处理
            wait();
        }

        this.socket = socket;
        avaliable = true;
        notifyAll();
    }

    /**
     * 等待请求 的具体实现
     * @return
     */
    private synchronized Socket await() throws InterruptedException {
        if(!avaliable){
            wait();
        }

        Socket socket = this.socket;
        avaliable = false;
        notifyAll();
        return socket;
    }

    /**
     * 解析http请求 填充 httpRequest 和 httpResponse
     * @param socket
     */
    private void process(Socket socket) throws IOException, ClassNotFoundException, InstantiationException, ServletException, IllegalAccessException {
        System.out.println("开始处理");
        httpRequest = new HttpRequest();
        httpResponse = new HttpResponse();
        httpRequest.setInputStream(socket.getInputStream());
        httpRequest.setOutputStream(socket.getOutputStream());
        httpResponse.setSocket(socket);
        SocketInputStream inputStream = new SocketInputStream(socket.getInputStream(),httpConnector.getBufferSize());
        parseConnection(socket);
        parseHeader(inputStream);
        parseRequest(inputStream);

        //调用默认的servlet 处理器 来处理请求
        httpConnector.getContainer().invoke(httpRequest,httpResponse);
    }

    /**
     * 解析请求头
     */
    private void parseHeader(SocketInputStream inputStream){
        //同 第三版
    }

    /**
     * 解析请求体
     */
    private void parseRequest(SocketInputStream inputStream){
        //同 第三版
    }

    /**
     * 解析链接
     */
    private void parseConnection(Socket socket){
        if(proxyPort != null){
            httpRequest.setPort(proxyPort);
        }else {
            httpRequest.setPort(socket.getPort());
        }
        InetAddress inetAddress = socket.getInetAddress();
        httpRequest.setInetAddress(inetAddress);
    }
}
