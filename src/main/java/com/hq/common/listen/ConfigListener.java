package com.hq.common.listen;

import com.google.common.collect.Maps;
import com.hq.common.cache.MapCache;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author: Mr.Huang
 * @create: 2019-07-30 15:11
 **/
@Slf4j
public class ConfigListener implements ServletContextListener {

    private static Map<String, String> conf = Maps.newHashMap();

    private MapCache cache = MapCache.single();
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();

        //项目发布,当前运行环境的绝对路径
        conf.put("realPath", sc.getRealPath("/").replaceFirst("/", ""));

        //servletContextPath,默认""
        conf.put("contextPath", sc.getContextPath());

        log.info("初始化配置：realPath:{}", conf.get("realPath"));
        log.info("初始化配置：contextPath:{}", conf.get("contextPath"));
        try {
            String host = InetAddress.getLocalHost().getHostAddress();
            cache.set("contextPath", sc.getContextPath());
            cache.set("host", host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        conf.clear();
    }
}
