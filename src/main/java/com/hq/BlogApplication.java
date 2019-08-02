package com.hq;

import com.hq.common.cache.MapCache;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableCaching
@MapperScan("com.hq.dao")
@Slf4j
public class BlogApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BlogApplication.class, args);
		String port = context.getEnvironment().getProperty("server.port");
		MapCache cache = MapCache.single();
		String basePath = "http://" + cache.get("host") + ":" + port + cache.get("contextPath");
		cache.set("basePath",  basePath);
		cache.set("blogUrl",  basePath + "/blog");
		cache.set("blogTitle", "sdisk");
		log.info("项目启动成功!访问地址:{}", basePath);
	}

}
