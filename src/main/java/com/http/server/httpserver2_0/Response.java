package com.http.server.httpserver2_0;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

/**
 * response
 * @author lucheng28
 * @date 2020-07-1
 */
public class Response implements ServletResponse {
    private String uri;
    private OutputStream outputStream;
    private Request request;
    private static final String header = "HTTP/1.1 200 ok\r\n" + "Content-Type: text/html\r\n" + "Content-Length: 13\r\n" +"\r\n";
    private static final String errorMsg = "<h1>file not found</h1>\r\n";
    private static final String root = "D:\\httpserver\\src\\main\\resources";
    private Integer len;

    public Response (OutputStream outputStream){
        this.outputStream = outputStream;
    }
    public void setRequest(Request request){
        this.request = request;
    }
    public void handlerStaticResource() throws Exception{
        this.uri = request.getUri();

        File file = new File(root, uri);
        if (file.exists()) {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] fileData = new byte[1024];
            int ch = inputStream.read(fileData);
            outputStream.write(header.getBytes());
            while (ch != -1) {
                outputStream.write(fileData, 0, ch);
                ch = inputStream.read(fileData);
            }
        } else {
            outputStream.write((header + errorMsg).getBytes());
        }
        outputStream.flush();
    }
    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(outputStream,true);
    }

    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentLengthLong(long l) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
