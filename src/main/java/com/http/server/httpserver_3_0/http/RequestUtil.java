package com.http.server.httpserver_3_0.http;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lucheng28
 * @date 2020-07-19
 * http解析工具
 */
public class RequestUtil {
    public static Cookie[] parseCookies(String value){
        if(StringUtils.isEmpty(value)){
            return new Cookie[0];
        }

        List<Cookie> cookies = new ArrayList<>();
        int pos = value.indexOf(";");
        if(pos > 0){
            //表示有多个cookie
            while(value.length() > 0){
                String $c = value.substring(0,pos);
                String[] $c2 = $c.split("=");
                Cookie $c3 = new Cookie($c2[0],$c2[1]);
                cookies.add($c3);

                value = value.substring(pos + 1);
                pos = value.indexOf(";");
            }
        }else{
            String[] cookie = value.split("=");
            Cookie $c = new Cookie(cookie[0],cookie[1]);
            cookies.add($c);
        }
        return cookies.toArray(new Cookie[cookies.size()]);
    }
}
