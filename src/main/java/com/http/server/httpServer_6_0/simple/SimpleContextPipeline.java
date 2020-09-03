package com.http.server.httpServer_6_0.simple;

import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;
import com.http.server.httpServer_5_0.rely.Value;
import com.http.server.httpServer_5_0.rely.ValueContext;
import com.http.server.httpServer_6_0.rely.Containter;
import com.http.server.httpServer_6_0.value.SimpleContextBasicValue;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 管道
 * @author lucheng28
 * @date 2020-07-31
 */
public class SimpleContextPipeline {
    //阈
    private List<Value> values;
    //基础阈
    private Value baseic;
    //关联容器
    private Containter containter;

    public SimpleContextPipeline(Containter containter){
        this.containter = containter;
        baseic = new SimpleContextBasicValue(this);
        values = new ArrayList<>();
    }

    public void addValue(Value value){
        values.add(value);
    }

    public void removeValue(Value value){
        values.remove(value);
    }

    //阈遍历 处理请求
    public void invoke(HttpRequest httpRequest, HttpResponse httpResponse) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ServletException, IOException, InterruptedException {
        new SimpleContextValueContext().invokeNext(httpRequest,httpResponse);
    }

    /**
     * context 内部value 迭代器
     */
    private class SimpleContextValueContext implements ValueContext{
        int stage = 0;
        @Override
        public void invokeNext(HttpRequest httpRequest, HttpResponse httpResponse) throws ServletException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
            int cur = stage;
            stage = stage + 1;
            if(cur < values.size()){
                values.get(cur).invokeNext(httpRequest,httpResponse,this);
            }else if(cur <= values.size() && stage > values.size()){
                baseic.invokeNext(httpRequest,httpResponse,this);
            }
        }
    }

    /**
     * 获取关联的容器
     * @return
     */
    public Containter getContainter(){
        return containter;
    }
}
