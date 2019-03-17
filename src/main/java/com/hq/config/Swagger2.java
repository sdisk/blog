package com.hq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by huang on 17/3/2019.
 */
@Configuration
@EnableSwagger2
public class Swagger2
{
    /**
     * swagger2开关
     */
    @Value("${spring.swagger.show}")
    private boolean swaggerShow;

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerShow)
                .apiInfo(apiInfo())
                .select()
                //当前包的路径
                .apis(RequestHandlerSelectors.basePackage("com.hq.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 构建api文档的详细信息函数
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                //页面标题
                .title("Blog Swagger Restful API")
                //文档描述
                .description("API 描述")
                .termsOfServiceUrl("https://github.com/sdisk/blog")
                .contact("sdisk")
                .version("1.0")
                .build();
    }
}
