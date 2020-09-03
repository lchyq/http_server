package com.http.server.httpServer_7_0;

import java.util.Map;

/**
 *  Filter定义类 tomcat中有 此类
 *  对应于 servlet配置中的 filter配置
 *  @author lucheng28
 *  @date 2020-09-01
 *  简单实现
 */
public class FilterDef {
    //过滤器描述
    private String desc;
    //过滤器名称
    private String filterName;
    //过滤器全限定名
    private String filterClass;
    //属性参数
    private Map<String,String> parameters;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterClass() {
        return filterClass;
    }

    public void setFilterClass(String filterClass) {
        this.filterClass = filterClass;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}
