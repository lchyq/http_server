package com.http.server.httpserver_8_0.load;

import com.http.server.httpserver_8_0.load.resource.ResourceEntry;
import org.apache.commons.lang3.StringUtils;

import javax.naming.directory.DirContext;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 应用类加载器 tomcat中的自定义类加载器需要是该类的子类
 * @author lucheng28
 * @date 2020-09-08
 */
public class WebAppClassLoader extends URLClassLoader {
    //父类加载器
    private ClassLoader parent;
    //缓存已经加载的类
    private HashMap<String,Object> resourceMap = new HashMap<>();
    //缓存没有找到的类
    private List<String> notResource = new ArrayList<>();
    //需要热部署的文件
    private File[] files = new File[0];
    //仓库
    private String[] repositories = new String[0];
    //是否需要热加载
    private boolean reload;
    //是否需要委托给父类
    private boolean delegate;
    //是否有额外的仓库地址
    private boolean hasExternalRepositories;
    //资源文件
    private DirContext resource;

    public WebAppClassLoader(){
        super(new URL[0]);
        this.parent = getParent();
    }
    public WebAppClassLoader(ClassLoader parent){
        super(new URL[0],parent);
        this.parent = parent;

    }

    public void reload(boolean reload){
        this.reload = reload;
    }
    public Boolean getLoaded(){
        return this.reload;
    }

    public void setDelegate(boolean delegate){this.delegate = delegate;}
    public boolean delegate(){return this.delegate;}

    /**
     * 添加仓库
     * @param path
     */
    public void addRepository(String path) {
        if(StringUtils.isNotEmpty(path) && (path.startsWith("/WEB-INF/lib") || path.startsWith("/WEB-INF/classes"))){
            //web服务器的默认仓库路径无需添加
            return;
        }
        try{
            super.addURL(new URL(path));
            hasExternalRepositories = true;
        }catch (MalformedURLException ma){
            throw new IllegalStateException(ma.toString());
        }
    }
    public void addClassPathRepositories(String repository){
        if(StringUtils.isNotEmpty(repository)){
            List<String> repositoryList = Arrays.asList(repositories);
            if(!repositoryList.contains(repository)){
                String[] newRep = new String[repositories.length + 1];
                System.arraycopy(repositories,0,newRep,0,repositories.length);
                newRep[repositories.length] = repository;
                repositories = newRep;
            }
        }
    }

    public Class loadClass0(String name) throws ClassNotFoundException {
        return loadClass(name,false);
    }
    /**
     * 破坏双亲委派
     * 破坏双亲委派的实现方式 需要重写loadClass方法，但是很明显 除了本工程src下的类可以加载到，
     * 其他的类都无法加载，比如 object类无法加载，servlet类无法加载
     * 解决方式我们可以多写几个 repository,包含 java中的rt.jar即可
     * 也可以对tomcat实现权限限制 对某些路径限制加载也可以实现
     *
     * 若不想破坏双亲委派则直接实现findClass即可
     * @param name
     * @return
     */
    public Class loadClass(String name,boolean resolve) throws ClassNotFoundException {
        if(StringUtils.isEmpty(name) || StringUtils.isBlank(name)){
            throw new RuntimeException("class name is empty");
        }

        //搜索本地是否缓存了该类
        if(notResource.contains(name)){
            throw new ClassNotFoundException();
        }

        Class c = findLocalLoadedClass(name);
        if(c != null){
            if(resolve){
                resolveClass(c);
            }
            return c;
        }

        //搜索内存中是否缓存了该类
        c = findLoadedClass(name);
        if(c != null){
            if(resolve){
                resolveClass(c);
            }
            return c;
        }

        //判断父类加载器是否存在
//        if(parent != null){
//            c = parent.loadClass(name);
//            if(c != null){
//                if(resolve){
//                    resolveClass(c);
//                }
//                return c;
//            }
//        }

        //判断是否委托给父类加载加载
        if(delegate){
            if(parent == null){
                parent = getSystemClassLoader();
            }
            c = parent.loadClass(name);
            if(c != null){
                if(resolve){
                    resolveClass(c);
                }
                return c;
            }
        }

        //本地仓库搜索
        c = findClass(name);
        if(c != null){
            if(resolve){
                resolveClass(c);
            }
            return c;
        }

        //没有查询到相关类，抛出异常
        throw new ClassNotFoundException();
    }
    public Class findClass(String name) throws ClassNotFoundException {
        //本地仓库获取相关的类
        //若没有设置本地仓库 或者没有设置三方仓库
        //则无法获取到类文件
        if (repositories.length == 1 && StringUtils.isEmpty(repositories[0]) && !hasExternalRepositories) {
            throw new ClassNotFoundException();
        }

        try {
            String namePath = name.replace(".", "\\");
            String classPath = namePath + ".class";
            ResourceEntry resourceEntry = null;
            for (int i = 0; i < repositories.length; i++) {
                String classFullPath = repositories[i] + classPath;
                Object res = resource.lookup(classFullPath);
                if (res instanceof ResourceEntry) {
                    resourceEntry = (ResourceEntry) res;
                }
                if(resourceEntry != null){
                    return resourceEntry.getResourceClass();
                }
                //使用系统类加载器获取类
                if(checkPermission(repositories[i])){
                    return getSystemClassLoader().loadClass(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //获取本地缓存的类
    private Class findLocalLoadedClass(String name){
        ResourceEntry resourceEntry = null;
        if(StringUtils.isNotEmpty(name)){
            resourceEntry = (ResourceEntry)resourceMap.get(name);
        }
        if(resourceEntry != null){
            return resourceEntry.getResourceClass();
        }
        notResource.add(name);
        return null;
    }

    //设置资源文件
    public void setResorce(DirContext resorce) {
        this.resource = resorce;
    }
    //资源权限校验
    private boolean checkPermission(String path){
        //此处资源加载已经在所有可以加载的仓库寻找过需要加载的资源，但是都没有找到
        //因此需要借系统类加载器来加载，对于系统类加载器我们需要最一些限制，不能所有的类都允许加载
        //此处简单实现，仅仅对下面的路径进行加载，其他的位置
        if("D:\\httpserver\\ROOT\\WEB-INF\\classes\\".equals(path)){
            return true;
        }
        // 若查找路径是 三方资源库的路径，也允许使用父类加载进行加载
        for (String repository : repositories) {
            if (repository.equals(path)) {
                return true;
            }
        }
        return false;
    }
}
