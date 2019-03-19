package com.hq.common.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: Mr.Huang
 * @create: 2019-03-19 10:00
 **/
public class XssFilter implements Filter {

    FilterConfig filterConfig = null;

    private List<String> urlExclusion = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //过滤xss攻击，wrap操作
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String servletPath = httpServletRequest.getServletPath();
        if (null != servletPath && urlExclusion.contains(servletPath)){
            chain.doFilter(request, response);
        } else{
            chain.doFilter(new XssHttpServletRequestWrapper(httpServletRequest), response);
        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

    public List<String> getUrlExclusion() {
        return urlExclusion;
    }

    public void setUrlExclusion(List<String> urlExclusion) {
        this.urlExclusion = urlExclusion;
    }
}
