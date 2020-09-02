package com.http.server.httpServer_7_0;

import com.http.server.httpServer_4_0.request.HttpRequest;
import com.http.server.httpServer_4_0.response.HttpResponse;
import com.http.server.httpServer_5_0.rely.Value;
import com.http.server.httpServer_6_0.rely.Containter;

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
    public void invoke(HttpRequest httpRequest, HttpResponse httpResponse){

    }
}
