package com.http.server.httpserver_3_0;

import com.http.server.httpserver_3_0.http.HttpRequest;
import com.http.server.httpserver_3_0.http.RequestUtil;
import com.http.server.httpserver_3_0.stream.HttpHeader;
import com.http.server.httpserver_3_0.stream.HttpRequestLine;
import com.http.server.httpserver_3_0.stream.SocketInputStream;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 处理器
 * 主要用来解析http请求
 * 并填充 httpRequest 和 httpResponse
 * @author lucheng28
 * @date 2020-07-06
 */
public class HttpProcessor {
    //存储http请求行信息
    private HttpRequestLine requestLine = new HttpRequestLine();
    private HttpRequest httpRequest;
    //存储http请求头信息
    private HttpHeader header = new HttpHeader();

    public HttpProcessor(){}
    public HttpProcessor(HttpRequest httpRequest){
        this.httpRequest = httpRequest;
    }

    //解析http请求及请求头
    public void process(SocketInputStream in, OutputStream ou) throws IOException {
        paresRequest(in,ou);
        parseHeader(in);
        print();
    }
    //解析request请求,并填充到 httpRequest
    private void paresRequest(SocketInputStream in, OutputStream ou) throws IOException {
        //将数据读取到 httpRequestLine
        in.readRequestLine(requestLine);
        //获取请求的方法 这里值得是 GET POST DELETE 等方法
        String method = new String(requestLine.method,0,requestLine.methodEnd);
        AtomicReference<String> uri = new AtomicReference<>();
        //协议 指http/1.1
        String protocol = new String(requestLine.protocol,0,requestLine.protocolEnd);

        if(method.length() < 1){
            throw new RuntimeException("Missing Http method");
        }

        if(requestLine.uriEnd < 1){
            throw new RuntimeException("Missing Http uri");
        }

        //获取查询参数
        parseQueryString(uri);

        //todo 判断绝对路径得逻辑先不管

        //判断uri中是否包含Jsessionid
        parseSessionId(httpRequest,uri);

        httpRequest.setMethod(method);
        httpRequest.setProtocol(protocol);
    }
    //解析http头信息
    //头信息是key：value结构
    public void parseHeader(SocketInputStream in) throws IOException {
        //读取header信息
        while(true){
            in.readHeader(header);
            if(header.nameEnd == 0){
                if(header.valueEnd == 0){
                    return;
                }else{
                    //头信息非法
                    throw new RuntimeException("illegal header info");
                }
            }

            //开始解析头信息
            String name = new String(header.name,0,header.nameEnd);
            String value = new String(header.value,0,header.valueEnd);
            httpRequest.addHeader(name,value);

            //判断头信息得类别
            if(name.equals("cookie")){
                //解析 cookie 信息
                Cookie[] cookies = RequestUtil.parseCookies(value);
                for (Cookie cookie : cookies) {
                    //判断当前request是否包含sessionId
                    if (!httpRequest.isRequestedSessionIdFromCookie()) {
                        //如果不包含sessionId
                        httpRequest.setRequestSessionId(true);
                        httpRequest.setJsessionid(cookie.getValue());
                    }
                    httpRequest.addCookies(name, value);
                }
            }else if(name.equals("content-type")){
                httpRequest.setContentType(value);
            }else if(name.equals("content-length")){
                httpRequest.setContentLength(Integer.parseInt(value));
            }
        }

    }

    public void setHttpRequest(HttpRequest httpRequest){
        this.httpRequest = httpRequest;
    }
    private void parseSessionId(HttpRequest httpRequest,AtomicReference<String> uri){
        String match = ";Jsessionid=";
        int pos = uri.get().indexOf(match);
        if(pos >= 0){
            //表示存在此标识符
            String rest = uri.get().substring(pos + match.length());
            int pos2 = rest.indexOf(";");
            if(pos2 >= 0){
                httpRequest.setJsessionid(rest.substring(0,pos2));
                System.out.println(rest.substring(0,pos2));
                rest = rest.substring(pos2);
            }else{
                httpRequest.setJsessionid(rest);
                rest = "";
            }
            httpRequest.setRequestSessionId(true);
            //除去sessionId重新拼装uri
            uri.getAndSet(uri.get().substring(0,pos) + rest);
            System.out.println(uri);
        }else{
            httpRequest.setRequestSessionId(false);
            httpRequest.setJsessionid(null);
        }
    }
    private void parseQueryString(AtomicReference<String> uri){
        int question = requestLine.indexOf("?");
        if(question >= 0){
            httpRequest.setQueryString(new String(requestLine.uri,question + 1,requestLine.uriEnd - question - 1));
            uri.getAndSet(new String(requestLine.uri,0,question));
        }else{
            //表示没有请求参数
            httpRequest.setQueryString(null);
            uri.getAndSet(new String(requestLine.uri,0,question));
        }
    }
    private void print(){
        System.out.println(httpRequest);
    }
}
