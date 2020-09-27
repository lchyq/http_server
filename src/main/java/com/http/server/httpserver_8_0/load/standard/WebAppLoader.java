package com.http.server.httpserver_8_0.load.standard;

import com.http.server.httpServer_6_0.rely.Containter;
import com.http.server.httpServer_6_0.simple.SimpleContext;
import com.http.server.httpserver_8_0.load.Loader;
import com.http.server.httpserver_8_0.load.WebAppClassLoader;
import com.http.server.httpserver_8_0.load.resource.ResourceDirContext;
import com.http.server.httpserver_8_0.load.resource.ResourceEntry;
import org.apache.commons.lang3.StringUtils;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.servlet.ServletContext;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;

/**
 * 应用类加载器 tomcat中有此实现
 * @author lucheng28
 * @date 2020-09-08
 *
 */
public class WebAppLoader implements Loader {
    //内部依赖的类加载器
    private WebAppClassLoader webAppClassLoader;
    //默认实现得类加载器
    private static final String defClassLoaderString = "com.http.server.httpserver_8_0.load.WebAppClassLoader";
    //用户自定义类加载器
    private String classLoaderString = null;
    //应用级别资源缓存
    private HashMap<String, ResourceEntry> resourceMap = new HashMap<>();
    //资源仓库
    private String[] repositories = new String[1];
    private ByteArrayOutputStream bos = new ByteArrayOutputStream();
    //servlet上下文临时存放目录
    public static final String WORK_DIR_ATTR = "javax.servlet.context.tempdir";
    //关联容器
    private Containter containter;
    //是否设置了本地仓库
    private boolean setRep = false;

    @Override
    public void setClassLoaderString(String classLoaderString) {
        if (StringUtils.isNotEmpty(classLoaderString)) {
            this.classLoaderString = classLoaderString;
        }
    }
    @Override
    public WebAppClassLoader createWebAppClassLoader() throws IllegalAccessException, InstantiationException,
            ClassNotFoundException, IOException, NoSuchMethodException, InvocationTargetException, NamingException {

        ObjectOutputStream oos = null;
        if (webAppClassLoader != null) {
            return webAppClassLoader;
        }
        if (StringUtils.isNotEmpty(classLoaderString)) {
            //检查本地缓存是否缓存该类
            Class webAppClassLoaderClass = findLoadedResource(classLoaderString);
            if (webAppClassLoaderClass != null) {
                return (WebAppClassLoader) webAppClassLoaderClass.newInstance();
            }

            //利用系统加载器加载用户自定义类
            webAppClassLoaderClass = Class.forName(classLoaderString);
            if (webAppClassLoaderClass != null) {
                oos = new ObjectOutputStream(bos);
                oos.writeObject(webAppClassLoaderClass);
                ResourceEntry resourceEntry = new ResourceEntry();
                resourceEntry.setResourceName(webAppClassLoaderClass.getName());
                resourceEntry.setResourceClass(webAppClassLoaderClass);
                resourceEntry.setResourceNameByte(webAppClassLoaderClass.getName().getBytes());
                resourceEntry.setResourceClassByte(bos.toByteArray());
                Date lastModified = new Date();
                resourceEntry.setLastModified(lastModified);
                resourceEntry.setLastModifiedTime(lastModified.getTime());
                resourceMap.put(classLoaderString, resourceEntry);
                oos.close();
                bos.close();
                return (WebAppClassLoader) webAppClassLoaderClass.newInstance();
            } else {
                throw new ClassNotFoundException();
            }
        }

        Class clazz = Class.forName(defClassLoaderString);
        webAppClassLoader = (WebAppClassLoader) clazz.newInstance();

        //设置仓库
        setRepository();
        //设置访问权限，此处的访问权限设置也是针对具体的路径来世设置对应的权限
        setPermission();

        return webAppClassLoader;
    }

    @Override
    public boolean delegate() {
        return webAppClassLoader.delegate();
    }
    @Override
    public void setDelegate(boolean delegate) {
        webAppClassLoader.setDelegate(delegate);
    }

    @Override
    public Class load(String name) throws ClassNotFoundException {
        return webAppClassLoader.loadClass0(name);
    }

    @Override
    public String[] findRepositories() {
        return this.repositories;
    }
    @Override
    public void addRepository(String repository) {
        //更新仓库列表
        String[] newRep = new String[repositories.length + 1];
        System.arraycopy(repositories,0,newRep,0,repositories.length);
        newRep[repositories.length] = repository;
        repositories = newRep;
        //更新classLoader的仓库列表
        webAppClassLoader.addRepository(repository);
    }

    @Override
    public void setReload(boolean reload) {
        webAppClassLoader.reload(reload);
        //todo 设置重载的同时 就需要启动线程来扫描资源了
    }
    @Override
    public Boolean reload() {
        return webAppClassLoader.getLoaded();
    }

    @Override
    public void setContainr(Containter containr) {
        this.containter = containr;
    }
    @Override
    public Containter getContainer() {
        return this.containter;
    }

    //获取已经缓存的资源
    private Class findLoadedResource(String resourceName) {
        ResourceEntry resourceEntry = resourceMap.get(resourceName);
        if (resourceEntry != null) {
            return resourceEntry.getResourceClass();
        }
        return null;
    }
    //设置仓库
    //此处模拟实现将 /WEB-INF/classes 加入到当前仓库
    //WEB-INF/lib的实现方式与此相同
    private synchronized void setRepository() throws NamingException, IOException {
        if(!(containter instanceof SimpleContext)){
            return;
        }

        if(setRep){
            return;
        }

        ServletContext servletContext = containter.getServletContext();
        if(servletContext == null){
            return;
        }

        String classPath = "\\WEB-INF\\classes\\";
        //判断是否存在 classPath路径
        DirContext dirResource = containter.getResource();
        //存放class 的仓库
        DirContext classes = null;
        //寻找 /WEB-INF/classes 是否有对应的目录路径
        Object classRepositoryDir = dirResource.lookup(classPath);
        try{
            if(classRepositoryDir instanceof DirContext){
                //若 获取到了 /WEB-INF/classes 对应的目录
                classes = (DirContext) classRepositoryDir;
            }
        }catch (Exception e){
            //表示没有获取到 /WEB-INF/classes 对应的目录，退出
            e.printStackTrace();
        }

        File classPathRealPathFile = null;
        if(classes != null){
            String classPathRealPath = servletContext.getRealPath(classPath);
            if(StringUtils.isNotEmpty(classPathRealPath)){
                //获取classPath真实地址
                classPathRealPathFile = new File(classPathRealPath);
            }else{
                //表示没有这个真实的地址，将class文件全部都拷贝到 javax.servlet.context.tempdir 文件地址下
                //说明在当前工程路径下找不到当前文件夹的地址
                File servletTempDir = (File) servletContext.getAttribute(WORK_DIR_ATTR);
                classPathRealPathFile = new File(servletTempDir,classPath);
                //创建对应的目录
                classPathRealPathFile.mkdirs();
                //将 /WEB-INF/classes 的文件数据拷贝到 javax.servlet.context.tempdir 对应的目录下
                //此处不做具体的实现
//                copyDir();
            }

            //添加到仓库地址中
            String [] newRep = new String[repositories.length + 1];
            System.arraycopy(repositories,0,newRep,0,repositories.length);
            newRep[repositories.length] = classPathRealPathFile.getCanonicalPath();
            repositories = newRep;
            webAppClassLoader.addClassPathRepositories(classPathRealPathFile.getCanonicalPath() + File.separator);
            setRep = true;
        }

    }
    private void setPermission(){
        //此处不做实现
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, NamingException {
        WebAppLoader webAppLoader = new WebAppLoader();
        SimpleContext container = new SimpleContext();
        DirContext dirContext = container.getResource();
        dirContext.bind("\\WEB-INF\\classes\\",new ResourceDirContext());
        webAppLoader.setContainr(container);
        System.setProperty("catalina.base","D:/httpserver/");
        WebAppClassLoader webAppClassLoader = webAppLoader.createWebAppClassLoader();
        webAppClassLoader.setResorce(dirContext);
        Class c = webAppLoader.load("com.http.server.httpserver2_0.servlet.MyServlet");
        System.out.println(c.getSimpleName());
    }
}
