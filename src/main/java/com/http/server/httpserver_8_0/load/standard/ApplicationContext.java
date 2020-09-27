package com.http.server.httpserver_8_0.load.standard;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.descriptor.JspConfigDescriptor;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * 全局servletContext
 * tomcat中有此实现，此处模拟实现，主要是为了获取tomcat初始化时各种配置属性
 * 以及路径,并不实现所有功能
 * @author lucheng28
 * @date 2020-09-22
 */
public class ApplicationContext implements ServletContext {
    //tomcat的安装目录
    private String docBase;
    //web应用的项目路径，项目一般都是在webapps目录下
    private String contextPath;
    private ServletContext servletContext;
    //context中的各种参数
    private Map<String,Object> parameters = new HashMap<>();
    //context中的各种属性
    private Map<String,Object> attribute = new HashMap<>();
    //一般当我们把contextPath设置成 "/"路径时 我们的项目文件一般都会在 root文件夹下
    private String defaultContextPath = "\\ROOT";
    //我们在自己电脑上安装tomcat时配置的路径
    private static final String docBaseAtt = "catalina.base";

    public ApplicationContext(){}
    public ApplicationContext(String docBase,String contextPath){
        this.docBase = docBase ;
        this.contextPath = contextPath;
    }
    @Override
    public String getContextPath() {
        if(StringUtils.isNotEmpty(contextPath)){
            return this.contextPath;
        }
        return defaultContextPath;
    }

    @Override
    public ServletContext getContext(String s) {
        return null;
    }
    public ServletContext getContext(){
        if(servletContext != null){
            return servletContext;
        }
        try{
            String docBasePath = System.getProperty(docBaseAtt);
            File path = new File(docBasePath);
            if(StringUtils.isNotEmpty(contextPath) && "/".equals(contextPath) || contextPath == null){
                contextPath = defaultContextPath;
            }
            servletContext = new ApplicationContext(path.getCanonicalPath(),contextPath);
            return servletContext;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getMajorVersion() {
        return 0;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public int getEffectiveMajorVersion() {
        return 0;
    }

    @Override
    public int getEffectiveMinorVersion() {
        return 0;
    }

    @Override
    public String getMimeType(String s) {
        return null;
    }

    @Override
    public Set<String> getResourcePaths(String s) {
        return null;
    }

    @Override
    public URL getResource(String s) throws MalformedURLException {
        return null;
    }

    @Override
    public InputStream getResourceAsStream(String s) {
        return null;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Override
    public RequestDispatcher getNamedDispatcher(String s) {
        return null;
    }

    @Override
    public Servlet getServlet(String s) throws ServletException {
        return null;
    }

    @Override
    public Enumeration<Servlet> getServlets() {
        return null;
    }

    @Override
    public Enumeration<String> getServletNames() {
        return null;
    }

    @Override
    public void log(String s) {

    }

    @Override
    public void log(Exception e, String s) {

    }

    @Override
    public void log(String s, Throwable throwable) {

    }

    @Override
    public String getRealPath(String s) {
        return docBase + contextPath + s;
    }

    @Override
    public String getServerInfo() {
        return null;
    }

    @Override
    public String getInitParameter(String s) {
        return (String) parameters.get(s);
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return null;
    }

    @Override
    public boolean setInitParameter(String s, String s1) {
        return parameters.put(s,s1) != null;
    }

    @Override
    public Object getAttribute(String s) {
        return attribute.get(s);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {
        attribute.put(s,o);
    }

    @Override
    public void removeAttribute(String s) {
        attribute.remove(s);
    }

    @Override
    public String getServletContextName() {
        return null;
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String s, String s1) {
        return null;
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String s, Servlet servlet) {
        return null;
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String s, Class<? extends Servlet> aClass) {
        return null;
    }

    @Override
    public <T extends Servlet> T createServlet(Class<T> aClass) throws ServletException {
        return null;
    }

    @Override
    public ServletRegistration getServletRegistration(String s) {
        return null;
    }

    @Override
    public Map<String, ? extends ServletRegistration> getServletRegistrations() {
        return null;
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String s, String s1) {
        return null;
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String s, Filter filter) {
        return null;
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String s, Class<? extends Filter> aClass) {
        return null;
    }

    @Override
    public <T extends Filter> T createFilter(Class<T> aClass) throws ServletException {
        return null;
    }

    @Override
    public FilterRegistration getFilterRegistration(String s) {
        return null;
    }

    @Override
    public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
        return null;
    }

    @Override
    public SessionCookieConfig getSessionCookieConfig() {
        return null;
    }

    @Override
    public void setSessionTrackingModes(Set<SessionTrackingMode> set) {

    }

    @Override
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        return null;
    }

    @Override
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        return null;
    }

    @Override
    public void addListener(String s) {

    }

    @Override
    public <T extends EventListener> void addListener(T t) {

    }

    @Override
    public void addListener(Class<? extends EventListener> aClass) {

    }

    @Override
    public <T extends EventListener> T createListener(Class<T> aClass) throws ServletException {
        return null;
    }

    @Override
    public JspConfigDescriptor getJspConfigDescriptor() {
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }

    @Override
    public void declareRoles(String... strings) {

    }

    @Override
    public String getVirtualServerName() {
        return null;
    }

    /**
     * 返回tomcat中默认的contextPath
     * @return
     */
    public String getDefaultContextPath(){
        return this.defaultContextPath;
    }
    public void setContextPath(String contextPath){
        this.contextPath = contextPath;
    }
}
