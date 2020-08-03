package com.http.server.httpServer_5_0.simple;
import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;
import com.http.server.httpServer_5_0.rely.Value;
import com.http.server.httpServer_5_0.simple.loader.Loader;
import com.http.server.httpServer_5_0.simple.value.BasicValue;
import com.http.server.httpServer_6_0.rely.Containter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 *  simpleWapper
 *  wapper 代表的是 tomcat中最小的servlet容器
 *  一个wapper容器表示一个servlet
 *  tomcat中实现自己的wapper容器需要实现 tomcat提供的wapper接口
 *  此处不做实现，仅学习使用
 * @author lucheng28
 * @date 2020-07-28
 */
public class SimpleWapper {
    //容器中的pipeline
    private SimplePipeline pipeline;
    //类加载器
    private Loader simpleLoader;
    //servlet 名称
    private String servletName;
    private Servlet servlet;
    //此处应该使用tomcat中的 container 接口替代
    //此处省略，但是含义相同
    private Containter parsent;

    /**
     * 构造方法用来初始化 pipeline 和 基础阈
     */
    public SimpleWapper(){
        pipeline = new SimplePipeline(this);
        pipeline.setBaseValue(new BasicValue(pipeline));
    }

    /**
     * 设置关联的servlet
     */
    public void setServlet(String servletName){
        this.servletName = servletName;
    }

    /**
     * 该方法属于 container接口中的方法
     * @param loader
     */
    public void setLoader(Loader loader){
        this.simpleLoader = loader;
    }

    /**
     * 该方法属于 container接口中的方法
     */
    public ClassLoader getLoader(){
        if(simpleLoader != null){
            return simpleLoader.getClassLoader();
        }
        return parsent.getLoader().getClassLoader();
    }

    /**
     * 添加阈
     * @param value
     */
    public void addValue(Value value){
        pipeline.addValue(value);
    }

    /**
     * 删除阈
     * @param value
     */
    public void removeValue(Value value){
        pipeline.removeValue(value);
    }

    /**
     * 设置基础阈
     * @param value
     */
    public void setBasicValue(Value value){pipeline.setBaseValue(value);}

    /**
     * wapper请求处理
     */
    public void invoke(HttpRequest httpRequest, HttpResponse httpResponse) throws ServletException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        pipeline.invokeNext(httpRequest,httpResponse);
    }

    /**
     * 获取wapper容器中的servlet
     * @return
     */
    public Servlet allocate() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if(servlet != null){
            return servlet;
        }
        Class clazz = getLoader().loadClass(servletName);
        return (Servlet)clazz.newInstance();
    }

    /**
     * 设置父容器
     * @param containter
     */
    public void setParsent(Containter containter){
        this.parsent = containter;
    }

    /**
     * 获取关联的父容器
     * @return
     */
    public Containter getParsent(){
        return parsent;
    }
}
