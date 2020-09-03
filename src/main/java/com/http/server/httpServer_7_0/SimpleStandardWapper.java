package com.http.server.httpServer_7_0;

import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;
import com.http.server.httpServer_5_0.rely.Value;
import com.http.server.httpServer_5_0.simple.SimpleWapper;
import com.http.server.httpServer_5_0.simple.loader.Loader;
import com.http.server.httpServer_6_0.rely.Containter;
import com.http.server.httpServer_6_0.rely.Mapper;
import com.http.server.httpServer_7_0.filter.config.GlobalApplicationFilterConfig;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Deque;
import java.util.Stack;

/**
 * 简版 标准wapper实现
 * @author lucheng28
 * @date 2020-09-01
 * 标准实现相比于之前的simpleWapper来说
 * 多了 STMServlet实现
 * 基础阈 实现上多了 过滤器的实现
 */
public class SimpleStandardWapper implements Containter {
    private SimplePipeline simplePipeline;
    private Containter parent;
    private String servletName;
    private String servletClass;
    private Servlet servlet;
    private Loader loader;
    private Stack<Servlet> servletPool;
    //是否stm servlet
    private boolean STM;
    //目前以添加的stmServlet数量
    private Integer countStmServlets = 0;
    //stmServlet实例数量
    private Integer nStmInstance = 0;
    //允许添加的最大的stmServlet数量
    private Integer maxStmInstance = 20;


    public SimpleStandardWapper(){
        simplePipeline = new SimplePipeline(this);
        simplePipeline.setBasicValue(new SimpleWapperBasicValue(simplePipeline));
        servletPool = new Stack<>();
        STM = false;
    }

    public Loader getLoader() {
        if(loader != null){
            return loader;
        }
        if(parent != null){
            return parent.getLoader();
        }
         throw new RuntimeException("loader is empty");
    }

    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    @Override
    public void setMapper(Mapper mapper) {
        throw new RuntimeException("illegal state！！");
    }

    @Override
    public Mapper getMapper() {
        return null;
    }

    @Override
    public void invoke(HttpRequest httpRequest, HttpResponse httpResponse) throws ClassNotFoundException, IOException, InstantiationException, ServletException, IllegalAccessException, InterruptedException {
        simplePipeline.invoke(httpRequest,httpResponse);
    }

    @Override
    public void addServletMapping(String pattren, String servletName) {
        throw new RuntimeException("illegal state！！");
    }

    @Override
    public String findServletMapping(String pattern) {
        throw new RuntimeException("illegal state！！");
    }

    @Override
    public void addChild(String servletName, SimpleStandardWapper containter) {
        throw new RuntimeException("illegal state！！");
    }

    @Override
    public SimpleStandardWapper findChild(String servletName) {
        throw new RuntimeException("illegal state！！");
    }

    @Override
    public void setFilterConfig(GlobalApplicationFilterConfig globalApplicationFilterConfig) {

    }

    @Override
    public GlobalApplicationFilterConfig getGlobalApplicationFilterConfig() {
        return null;
    }

    public Servlet allocate() throws IllegalAccessException, InstantiationException, ClassNotFoundException, InterruptedException {
        if(!STM){
            if(servlet == null){
                servlet =  (Servlet) getLoader().load(servletClass);
                return servlet;
            }else{
                return servlet;
            }
        }else{
            synchronized (servletPool){
                if(countStmServlets < nStmInstance){
                    servletPool.push((Servlet) getLoader().load(servletClass));
                    nStmInstance ++;
                }
                while(countStmServlets > nStmInstance){
                    if(nStmInstance < maxStmInstance){
                        servletPool.push((Servlet) getLoader().load(servletClass));
                        nStmInstance ++;
                    }else{
                        servletPool.wait();
                    }
                }
                countStmServlets ++;
                nStmInstance --;
                return servletPool.pop();
            }
        }
    }

    public Containter getParent() {
        return parent;
    }

    public void setParent(Containter parent) {
        this.parent = parent;
    }

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public String getServletClass() {
        return servletClass;
    }

    public void setServletClass(String servletClass) {
        this.servletClass = servletClass;
    }

    public boolean isSTM() {
        return STM;
    }

    public void setSTM(boolean STM) {
        this.STM = STM;
    }

    public Integer getnStmInstance() {
        return nStmInstance;
    }

    public void setnStmInstance(Integer nStmInstance) {
        this.nStmInstance = nStmInstance;
    }

    public Integer getMaxStmInstance() {
        return maxStmInstance;
    }

    public void setMaxStmInstance(Integer maxStmInstance) {
        this.maxStmInstance = maxStmInstance;
    }

    public void addValue(Value value){
        simplePipeline.addValue(value);
    }
    public void removeValue(Value value){
        simplePipeline.removeValue(value);
    }
}
