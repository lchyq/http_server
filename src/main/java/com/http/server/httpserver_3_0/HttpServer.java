package com.http.server.httpserver_3_0;

/**
 * 本次将server中的功能剥离出来
 * 抽象出httpConnector 和 httpProcessor
 * httpConnector 负责等待http请求
 * httpProcessor 负责为每个请求建立 httpRequest、httpResponse
 */
public class HttpServer {
    public static void main(String[] args) {
        HttpConnector httpConnector = new HttpConnector();
        httpConnector.conn();
    }
}
