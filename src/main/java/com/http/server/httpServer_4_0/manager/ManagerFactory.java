package com.http.server.httpServer_4_0.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * ManagerFactory
 * @author lucheng28
 * @date 2020-07-26
 */
public class ManagerFactory {
    private static String resourceRoot = "LocalStrings";
    private static Map<String, ResourceBundle> resourceBundleMap = new HashMap<>();
    public static MyStringManager get(String packName){
        MyStringManager manager = new MyStringManager();
        if(packName != null && !"".equals(packName)){
            ResourceBundle resourceBundle = resourceBundleMap.computeIfAbsent(packName,e -> ResourceBundle.getBundle(packName));
            manager.setResourceBundle(resourceBundle);
        }else{
            ResourceBundle resourceBundle = resourceBundleMap.computeIfAbsent(resourceRoot,e -> ResourceBundle.getBundle(resourceRoot));
            manager.setResourceBundle(resourceBundle);
        }
        return manager;
    }
}
