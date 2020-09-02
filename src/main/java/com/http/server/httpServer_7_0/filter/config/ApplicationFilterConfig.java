package com.http.server.httpServer_7_0.filter.config;

import com.http.server.httpServer_7_0.FilterDef;

/**
 * filter 配置
 * @author lucheng28
 * @date 2020-09-02
 * 简单模拟tomcat中的实现
 */
public class ApplicationFilterConfig {
    //关联的 filter定义
    private FilterDef def;

    public FilterDef getDef() {
        return def;
    }

    public void setDef(FilterDef def) {
        this.def = def;
    }
}
