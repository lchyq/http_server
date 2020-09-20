package com.http.server.httpserver_8_0.load;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

/**
 * 应用类加载器 tomcat中的自定义类加载器需要是该类的子类
 * @author lucheng28
 * @date 2020-09-08
 */
public class WebAppClassLoader extends URLClassLoader {
    //父类加载器
    private ClassLoader parent;
    //缓存已经加载的类
    private HashMap resourceMap = new HashMap();
    //缓存没有找到的类
    private HashMap notResourceMap = new HashMap();
    //需要热部署的文件
    private File[] files = new File[0];
    //是否需要热加载
    private boolean reload;
    //是否需要委托给父类
    private boolean delegate;
    private final String rep = "target\\classes\\";

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

    public Class loadClass0(String name) throws ClassNotFoundException {
        return loadClass(name);
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
    public Class loadClass(String name) throws ClassNotFoundException {
        if(StringUtils.isEmpty(name) || StringUtils.isBlank(name)){
            throw new RuntimeException("class name is empty");
        }
        String repository = System.getProperty("user.dir") + File.separator + rep;
        String classPathAbsloute = repository + name.replace(".","\\") + ".class";
        File file = new File(classPathAbsloute);
        Class clazz = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fileInputStream.read(data);
            clazz = defineClass(name,data,0,data.length);
        } catch (Exception e) {
            //对于 object、servlet类的加载，此时使用系统类加载器来加载
            //配合路径权限来实现对类加载器加载路径的限制
            clazz = getSystemClassLoader().loadClass(name);
            return clazz;
        }
        return clazz;
    }
}
