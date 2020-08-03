package com.http.server.httpServer_5_0.simple;

import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;
import com.http.server.httpServer_5_0.rely.Value;
import com.http.server.httpServer_5_0.rely.ValueContext;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管道
 * 类似于 web服务中的 过滤器
 * tomcat 中 实现自己的管道需要实现tomcat 提供的pipeLine接口
 * 管道的作用就是传递 http请求，并进行处理
 */
public class SimplePipeline {
    //容器中添加的阈
    private List<Value> values;
    //基础阈
    private Value baseValue;
    //此处的simpleWapper 应该是tomcat中的 Container接口
    //此处简单实用 simpleWapper来表示
    private SimpleWapper container;
    public SimplePipeline(SimpleWapper container){
        values = new ArrayList<>();
        this.container = container;
    }

    //遍历阈
    public void invokeNext(HttpRequest httpRequest, HttpResponse httpResponse) throws ServletException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        new SimpleWapperValueContext().invokeNext(httpRequest,httpResponse);
    }

    /**
     * tomcat中实现自己的 valueContext 是通过实现 tomcat 中提供的 ValueContext
     * @author lucheng28
     * @date 2020-07-28
     */
    private class SimpleWapperValueContext implements ValueContext {
        private int stage = 0;
        @Override
        public void invokeNext(HttpRequest httpRequest, HttpResponse httpResponse) throws ServletException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
            int cur = stage;
            stage = stage + 1;
            if(cur < values.size()){
                values.get(cur).invokeNext(httpRequest,httpResponse,this);
            }else if(cur <= values.size() && stage > values.size()){
                baseValue.invokeNext(httpRequest,httpResponse,this);
            }
        }
    }
    /**
     * 添加阈
     * @param value
     */
    public void addValue(Value value){
        values.add(value);
    }

    /**
     * 删除阈
     * @param value
     */
    public void removeValue(Value value){
        values.remove(value);
    }

    /**
     * 设置基础阈
     * @param value
     */
    public void setBaseValue(Value value){
        this.baseValue = value;
    }

    /**
     * 获取管道关联的容器
     * @return
     */
    public SimpleWapper getContainer(){
        return container;
    }
}
