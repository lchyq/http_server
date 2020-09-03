package com.http.server.httpServer_7_0.filter.config;

import com.http.server.httpServer_4_0.DefaultContainer;
import com.http.server.httpServer_5_0.simple.loader.Loader;
import com.http.server.httpServer_6_0.rely.Containter;
import com.http.server.httpServer_7_0.FilterDef;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.Filter;

/**
 * filter 配置
 * @author lucheng28
 * @date 2020-09-02
 * 简单模拟tomcat中的实现
 * filter 是与 servlet 绑定在一起的
 */
public class ApplicationFilterConfig {
    //关联的 filter定义
    private FilterDef def;
    private Containter containter;
    public ApplicationFilterConfig(Containter containter,FilterDef filterDef){
        this.containter = containter;
        this.def = filterDef;
    }

    public FilterDef getDef() {
        return def;
    }
    public void setDef(FilterDef def) {
        this.def = def;
    }
    public Filter getFilter() {
        try{
            String filterClass = def.getFilterClass();
            if(StringUtils.isEmpty(filterClass)){
                throw new RuntimeException("filter configuration is error");
            }
            Loader loader = containter.getLoader();
            return (Filter) loader.load(filterClass);
        }catch (Exception e){
            throw new RuntimeException("get filter error");
        }
    }
}
