package com.http.server.httpServer_7_0.filter.chain;

import com.http.server.httpServer_6_0.rely.Containter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * 过滤器链
 * @author lucheng28
 * @date 2020-09-02
 * 简单模拟tomcat中的实现
 */
public class ApplicationFilterChain {
    private List<Filter> filters;
    private Containter containter;
    public ApplicationFilterChain(){
        filters = new ArrayList<>();
    }
    //设置关联容器
    public void setContainter(Containter containter){
        this.containter = containter;
    }
    public Containter getContainter(){
        return containter;
    }
}
