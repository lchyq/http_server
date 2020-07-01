package com.http.server.httpserver1_0;

import java.io.InputStream;

/**
 * request
 * @author lucheng28
 * @date 2020-06-29
 */
public class Request {
    private InputStream stream;
    private String uri;

    public Request(){}
    public Request(InputStream stream){
        this.stream = stream;
    }

    public String parse() throws Exception{
        StringBuilder stringBuilder = new StringBuilder(2048);
        byte[] bytes = new byte[2048];

        int i = stream.read(bytes);
        for(int j = 0;j < i;j++){
            stringBuilder.append((char) bytes[j]);
        }
        System.out.println("收到请求："+stringBuilder.toString());
        return uri(stringBuilder.toString());
    }

    private String uri(String url){
        int index = url.indexOf(' ');
        int index2 = url.indexOf(' ',index + 1);
        if(index2 > index){
            return url.substring(index + 1,index2);
        }
        return null;
    }
}
