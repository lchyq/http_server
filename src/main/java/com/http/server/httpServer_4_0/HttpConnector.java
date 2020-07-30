package com.http.server.httpServer_4_0;

import com.http.server.httpServer_4_0.manager.ManagerFactory;
import com.http.server.httpServer_4_0.rely.Lifecycle;
import com.http.server.httpServer_4_0.rely.LifecycleException;
import com.http.server.httpServer_4_0.rely.LifecycleListener;
import com.http.server.httpServer_5_0.simple.SimpleWapper;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

/**
 * @author lucheng28
 * @date 2020-07-26
 * http连接器
 * 负责处理接受http请求
 * 分发http请求
 * 若是扩展tomcat的连接器实现，则需要实现tomcat提供的connector接口
 * 此处不做实现，仅作学习
 * Lifecycle 接口用来控制 连接器的生命周期
 * 创建连接器时调用 start方法 仅调用一次
 * 销毁时 调用 stop方法 仅调用一次
 * 连接器 与 处理器是 一对多的关系
 */
public class HttpConnector implements Runnable, Lifecycle {
    private String ip = null;
    private Integer port = 8080;
    private Integer backLog = 1024;
    //将 httpProcessor 池化，避免重复 创建
    private Stack<HttpProcessor> processorStack = new Stack<>();
    //服务区 套接字
    private ServerSocket serverSocket;
    //套接字 创建工厂
    private ServerSocketFactory  serverSocketFactory;
    //默认容器
    private SimpleWapper container;
    //是否初始化
    private boolean inited = false;
    //是否停止
    private boolean stoped = true;
    //processor 最小数量
    private Integer minProcessor = 5;
    //processor 最大数量
    private Integer maxProcessor = 20;
    //缓冲区大小
    private Integer bufferSize = 2048;
    //当前数量
    private int curProcessor;
    @Override
    public void addLifecycleListener(LifecycleListener listener) {

    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return new LifecycleListener[0];
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {

    }

    @Override
    public void start() throws LifecycleException, IOException {
        if(inited){
            System.out.println(ManagerFactory.get("").get("connector-initialized"));
        }
        else if(stoped){
            System.out.println(ManagerFactory.get("").get("connector-has-stop"));
            open();
            inited = true;
            stoped = false;
        }
    }

    @Override
    public void stop() throws LifecycleException {
        stoped = false;
    }

    @Override
    public void run() {
        //connector 的 主逻辑
        HttpProcessor httpProcessor = null;
        try {
            while(!stoped){
                Socket socket = serverSocket.accept();
                System.out.println("接收到请求");
                if(curProcessor > maxProcessor){
                    System.out.println(ManagerFactory.get("").get("processor-has-exhausted"));
                    socket.close();
                    continue;
                }
                else if(curProcessor <= minProcessor){
                    httpProcessor = processorStack.pop();
                }
                else if(curProcessor >= minProcessor && curProcessor < maxProcessor){
                    httpProcessor = new HttpProcessor(String.format("HttpProcess-%s-%s", ++curProcessor, port), port,this);
                    httpProcessor.start();
                }
                if(httpProcessor != null){
                    httpProcessor.assign(socket);
                    continue;
                }
                throw new RuntimeException(ManagerFactory.get("").get("processor-has-exhausted"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 套接字初始化
     */
    public void init() throws LifecycleException, IOException {
        start();
    }

    /**
     * 启动
     */
    public void connectorStart(){
        Thread thread = new Thread(this, "HttpConnector-Thread-----");
        thread.setDaemon(true);
        thread.start();

        //初始化 processor
        while (curProcessor < minProcessor) {
            HttpProcessor httpProcessor = new HttpProcessor(String.format("HttpProcess-%s-%s", ++curProcessor, port), port,this);
            httpProcessor.start();
            recycle(httpProcessor);
        }
    }

    /**
     * 绑定servlet容器
     * @param container
     */
    public void setContainer(SimpleWapper container){
        this.container = container;
    }

    /**
     * 开启
     * @throws IOException
     */
    private synchronized void open() throws IOException {
        if(serverSocketFactory == null){
            serverSocketFactory = new ServerSocketFactory();
        }
        if(serverSocket == null){
            if(ip == null){
                serverSocket = serverSocketFactory.createServerSocket(port,backLog);
            }else {
                serverSocket = serverSocketFactory.createServerSocket(ip,port,backLog);
            }
        }
    }

    /**
     * 创建服务器套接字
     */
    private class ServerSocketFactory{
        //创建绑定 本机的 服务器套接字
        public ServerSocket createServerSocket(Integer port,Integer backLog) throws IOException {
            serverSocket = new ServerSocket(port,backLog);
            return serverSocket;
        }

        //创建 指定ip的 服务器套接字
        public ServerSocket createServerSocket(String ip,Integer port,Integer backLog) throws IOException {
            InetAddress inet4Address = Inet4Address.getByAddress(ip.getBytes());
            serverSocket = new ServerSocket(port,backLog,inet4Address);
            return serverSocket;
        }
    }

    /**
     * 回收HttpProcessor
     */
    public void recycle(HttpProcessor httpProcessor){
        processorStack.push(httpProcessor);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getBackLog() {
        return backLog;
    }

    public void setBackLog(Integer backLog) {
        this.backLog = backLog;
    }

    public Integer getMinProcessor() {
        return minProcessor;
    }

    public void setMinProcessor(Integer minProcessor) {
        this.minProcessor = minProcessor;
    }

    public Integer getMaxProcessor() {
        return maxProcessor;
    }

    public void setMaxProcessor(Integer maxProcessor) {
        this.maxProcessor = maxProcessor;
    }

    public Integer getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(Integer bufferSize) {
        this.bufferSize = bufferSize;
    }

    public SimpleWapper getContainer() {
        return container;
    }
}
