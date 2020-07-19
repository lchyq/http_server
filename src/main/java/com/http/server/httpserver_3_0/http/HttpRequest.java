package com.http.server.httpserver_3_0.http;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

/**
 * HttpRequest 类
 * 负责获取查询参数
 * @author lucheng28
 */
public class HttpRequest implements HttpServletRequest {
    private String queryString;
    private String Jsessionid;
    //是否存在Jsessionid
    private boolean bol;
    private String method;
    private String protocol;
    //存储cookie信息
    private List<Cookie> cookies = new ArrayList<>();
    //存储http头信息
    private Map<String,ArrayList<String>> headers = new HashMap<>();
    //数据类型
    private String contentType;
    //数据长度
    private Integer contentLength;
    @Override
    public String getAuthType() {
        return null;
    }

    @Override
    public Cookie[] getCookies() {
        Cookie[] cookie = new Cookie[cookies.size()];
        for(int i = 0;i < cookies.size();i++){
            cookie[i] = cookies.get(i);
        }
        return cookie;
    }

    @Override
    public long getDateHeader(String s) {
        return 0;
    }

    @Override
    public String getHeader(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getHeaders(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return null;
    }

    @Override
    public int getIntHeader(String s) {
        return 0;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getPathInfo() {
        return null;
    }

    @Override
    public String getPathTranslated() {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public String getQueryString() {
        return queryString;
    }

    @Override
    public String getRemoteUser() {
        return null;
    }

    @Override
    public boolean isUserInRole(String s) {
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        return Jsessionid;
    }

    @Override
    public String getRequestURI() {
        return null;
    }

    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    @Override
    public String getServletPath() {
        return null;
    }

    @Override
    public HttpSession getSession(boolean b) {
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public String changeSessionId() {
        return null;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return bol;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    @Override
    public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
        return false;
    }

    @Override
    public void login(String s, String s1) throws ServletException {

    }

    @Override
    public void logout() throws ServletException {

    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return null;
    }

    @Override
    public Part getPart(String s) throws IOException, ServletException {
        return null;
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> aClass) throws IOException, ServletException {
        return null;
    }

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    @Override
    public int getContentLength() {
        return contentLength;
    }

    @Override
    public long getContentLengthLong() {
        return 0;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getParameter(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String s) {
        return new String[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Override
    public String getRealPath(String s) {
        return null;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    @Override
    public DispatcherType getDispatcherType() {
        return null;
    }

    /**
     * 设置查询字符串
     * @param query
     */
    public void setQueryString(String query){
        this.queryString = query;
    }

    /**
     * 设置sessionId
     * @param jsessionid
     */
    public void setJsessionid(String jsessionid){
        this.Jsessionid = jsessionid;
    }

    /**
     * 是否存在sessionId
     * @param bol
     */
    public void setRequestSessionId(boolean bol) {
        this.bol = bol;
    }

    /**
     * 设置请求方法
     * @param method
     */
    public void setMethod(String method){
        this.method = method;
    }

    /**
     * 设置请求协议
     * @param protocol
     */
    public void setProtocol(String protocol){
        this.protocol = protocol;
    }

    /**
     * 添加头信息
     * @param name
     * @param value
     */
    public void addHeader(String name,String value){
        name  = name.toLowerCase();
        ArrayList<String> values = headers.get(name);
        if(values == null){
            values = new ArrayList<>();
            headers.put(name,values);
        }
        values.add(value);
    }

    /**
     * 添加cookie
     * @param name
     * @param value
     */
    public void addCookies(String name,String value){
        Cookie cookie = new Cookie(name,value);
        cookies.add(cookie);
    }

    /**
     * 设置数据类型
     * @param contentType
     */
    public void setContentType(String contentType){
        this.contentType = contentType;
    }

    /**
     * 设置数据长度
     * @param contentLength
     */
    public void setContentLength(Integer contentLength){
        this.contentLength = contentLength;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "queryString='" + queryString + '\'' +
                ", Jsessionid='" + Jsessionid + '\'' +
                ", bol=" + bol +
                ", method='" + method + '\'' +
                ", protocol='" + protocol + '\'' +
                ", cookies=" + cookies +
                ", headers=" + headers +
                ", contentType='" + contentType + '\'' +
                ", contentLength=" + contentLength +
                '}';
    }
}
