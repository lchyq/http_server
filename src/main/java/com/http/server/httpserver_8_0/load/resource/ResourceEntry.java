package com.http.server.httpserver_8_0.load.resource;

import java.util.Date;

/**
 * 资源类
 * @author lucheng28
 * @date 2020-09-09
 * 在tomcat中表示的是 tomcat中的资源文件 例如servlet
 */
public class ResourceEntry {
    private long lastModifiedTime;
    private Date lastModified;
    //资源的全限定名
    private String resourceName;
    //资源的类
    private Class resourceClass;
    //资源名称流
    private byte[] resourceNameByte;
    //资源类流
    private byte[] resourceClassByte;

    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Class getResourceClass() {
        return resourceClass;
    }

    public void setResourceClass(Class resourceClass) {
        this.resourceClass = resourceClass;
    }

    public byte[] getResourceNameByte() {
        return resourceNameByte;
    }

    public void setResourceNameByte(byte[] resourceNameByte) {
        this.resourceNameByte = resourceNameByte;
    }

    public byte[] getResourceClassByte() {
        return resourceClassByte;
    }

    public void setResourceClassByte(byte[] resourceClassByte) {
        this.resourceClassByte = resourceClassByte;
    }
}
