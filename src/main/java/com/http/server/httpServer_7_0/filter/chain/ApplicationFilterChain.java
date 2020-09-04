package com.http.server.httpServer_7_0.filter.chain;

import com.http.server.httpServer_7_0.filter.config.ApplicationFilterConfig;
import com.http.server.httpServer_7_0.filter.config.GlobalApplicationFilterConfig;

import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 过滤器链
 * @author lucheng28
 * @date 2020-09-02
 * 简单模拟tomcat中的实现
 * 需要实现servlet中的 filterChain 接口
 */
public class ApplicationFilterChain implements FilterChain {
    private List<Filter> filters;
    private Servlet servlet;
    private Iterator<Filter> iterator;
    public ApplicationFilterChain(){
        filters = new ArrayList<>();
    }
    public void setServlet(Servlet servlet){
        this.servlet = servlet;
    }
    public ApplicationFilterChain initFilters(GlobalApplicationFilterConfig applicationFilterConfig){
        filters = applicationFilterConfig.getApplicationFilterConfigs().stream().map(ApplicationFilterConfig::getFilter).collect(Collectors.toList());
        this.iterator = filters.iterator();
        return this;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
        if(filters == null || servlet == null){
            throw new RuntimeException("filters is empty or servlet is null");
        }
        if (iterator.hasNext()){
            Filter filter = iterator.next();
            filter.doFilter(servletRequest,servletResponse,this);
        }else{
            servlet.service(servletRequest,servletResponse);
        }
    }
}
