package com.http.server.httpServer_7_0.filter.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局Filter配置
 * @author lucheng28
 * @date 2020-09-02
 */
public class GlobalApplicationFilterConfig {
    private List<ApplicationFilterConfig> applicationFilterConfigs;
    public GlobalApplicationFilterConfig(){
        applicationFilterConfigs = new ArrayList<>();
    }
    public List<ApplicationFilterConfig> getApplicationFilterConfigs(){
        return applicationFilterConfigs;
    }
    public void addFilterConfig(ApplicationFilterConfig applicationFilterConfig){
        applicationFilterConfigs.add(applicationFilterConfig);
    }
}
