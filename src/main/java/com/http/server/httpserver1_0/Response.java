package com.http.server.httpserver1_0;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * http响应信息
 * @author lucheng28
 * @date 2020-07-01
 */
public class Response {
    private OutputStream outputStream;
    private Request request;
    private String uri;
    private static Integer len = "<h1>hello word</h1>".getBytes().length;
    private static final String msg = "HTTP/1.1 200 success\r\n" + "Content-Type: text/html\r\n" + "Content-Length: "+len+ "\r\n" +"\r\n" +  "<h1>FILE NOT FOUND</h1>";
    private static final String header = "HTTP/1.1 200 success\r\n" + "Content-Type: text/html\r\n" + "Content-Length: "+13+ "\r\n" +"\r\n";
    public Response (OutputStream stream){
        this.outputStream = stream;
    }
    public void setStream(Request request){
        this.request = request;
    }
    public String sendUri() throws Exception {
        uri = request.parse();
        return uri;
    }
    public void sendStaticSource() throws IOException {
        if(uri.equals("/index.html")){
            File file = new File("D:\\httpserver","\\src\\main\\resources\\index.html");
            if(file.exists()){
                int total = 0;
                outputStream.write(header.getBytes());
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = fileInputStream.read(bytes);
                total = total + len;
                while(len != -1){
                    outputStream.write(bytes,0,len);
                    len = fileInputStream.read(bytes);
                    total = total + len;
                }
                System.out.println(total);
            }else{
                System.out.println("文件不存在");
                outputStream.write(msg.getBytes());
                outputStream.flush();
            }
        }
    }
}
