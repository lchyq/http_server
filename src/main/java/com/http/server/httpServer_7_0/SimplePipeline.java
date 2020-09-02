package com.http.server.httpServer_7_0;

import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;
import com.http.server.httpServer_5_0.rely.Value;
import com.http.server.httpServer_5_0.rely.ValueContext;
import com.http.server.httpServer_6_0.rely.Containter;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * simplePipeline
 * @author lucheng28
 * @date 2020-09-01
 */
public class SimplePipeline {
    private Containter containter;
    private List<Value> values;
    //基础阈
    private Value basicValue;

    public SimplePipeline(Containter containter){
        this.containter = containter;
        values = new ArrayList<>();
        basicValue = new BasicValue(this);
    }
    public void setBasicValue(Value basicValue){
        this.basicValue = basicValue;
    }
    public Value getBasicValue(){
        return basicValue;
    }
    public void addValue(Value value){
        values.add(value);
    }
    public void removeValue(Value value){
        values.remove(value);
    }
    public List<Value> getValues(){
        return values;
    }
    public void setContainter(Containter containter){
        this.containter = containter;
    }
    public Containter getContainter(){
        return containter;
    }
    public void invoke(HttpRequest httpRequest, HttpResponse httpResponse) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ServletException, IOException {
        new SimpleWapperValueContext().invokeNext(httpRequest,httpResponse);
    }

    //pipeline 内部迭代器
    private class SimpleWapperValueContext implements ValueContext{
        int index= 0;
        @Override
        public void invokeNext(HttpRequest httpRequest, HttpResponse httpResponse) throws ServletException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
            int cur = index;
            if(cur < values.size()){
                values.get(cur).invokeNext(httpRequest,httpResponse,this);
                index ++;
            }else{
                basicValue.invokeNext(httpRequest,httpResponse,this);
            }
        }
    }
}
