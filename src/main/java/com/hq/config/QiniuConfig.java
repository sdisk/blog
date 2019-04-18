package com.hq.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 七牛
 * @author: Mr.Huang
 * @create: 2019-04-18 15:20
 **/
@Configuration
@Getter
@Setter
public class QiniuConfig {

    @Value("${qiniu.access-key}")
    private String accessKey;

    @Value("${qiniu.secret-key}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.domain}")
    private String domain;
}
