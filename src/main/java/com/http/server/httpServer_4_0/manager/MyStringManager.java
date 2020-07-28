package com.http.server.httpServer_4_0.manager;

import java.util.ResourceBundle;

/**
 * stringManager
 * @author lucheng28
 * @date 2020-07-26
 */
public class MyStringManager {
    private ResourceBundle resourceBundle;
    public MyStringManager(String packName){
        this.resourceBundle = ResourceBundle.getBundle(packName);
    }
    public MyStringManager(){
    }
    public String get(String msgKey){
        if(msgKey != null){
            return resourceBundle.getString(msgKey);
        }
        return "";
    }
    public void setResourceBundle(ResourceBundle resourceBundle){
        this.resourceBundle = resourceBundle;
    }
}
