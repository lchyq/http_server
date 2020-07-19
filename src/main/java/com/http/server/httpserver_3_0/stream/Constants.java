package com.http.server.httpserver_3_0.stream;
import java.io.File;

/**
 * @author lucheng28
 * @date 2020-07-19
 */
public final class Constants {
  //user.dir获取的是项目的根目录
  public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator  + "webroot";
  public static final String Package = "LocalStrings";
  public static final int DEFAULT_CONNECTION_TIMEOUT = 60000;
  public static final int PROCESSOR_IDLE = 0;
  public static final int PROCESSOR_ACTIVE = 1;
}
