package com.hq.config;

import com.hq.utils.ToolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @description: 配置项
 * @author: Mr.Huang
 * @create: 2019-03-18 16:31
 **/
@Component
@ConfigurationProperties(prefix = BlogProperties.PREFIX)
public class BlogProperties {

    public static final String PREFIX = "tyche";

    private Boolean kaptchaOpen = false;

    private Boolean swaggerOpen = false;

    private Boolean springSessionOpen = false;

    private String fileUploadPath;

    private Boolean haveCreatePath = false;

    private Integer sessionInvalidateTime = 30 * 60;

    private Integer sessionValidationInterval = 15 * 60;

    public String getFileUploadPath(){
        //没有文件上传路径，保存到临时目录
        if (StringUtils.isEmpty(fileUploadPath)){
            return ToolUtil.getTempPath();
        } else {
            //判断有没有结尾符，没有则加上
            if (!fileUploadPath.endsWith(File.separator)){
                fileUploadPath += File.separator;
            }
            //判断目录是否存在，不存在则创建
            if (!haveCreatePath){
                File file = new File(fileUploadPath);
                file.mkdirs();
                haveCreatePath = true;
            }
            return fileUploadPath;
        }
    }

    public Boolean getSwaggerOpen() {
        return swaggerOpen;
    }

    public void setSwaggerOpen(Boolean swaggerOpen) {
        this.swaggerOpen = swaggerOpen;
    }

    public Boolean getSpringSessionOpen() {
        return springSessionOpen;
    }

    public void setSpringSessionOpen(Boolean springSessionOpen) {
        this.springSessionOpen = springSessionOpen;
    }

    public void setFileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
    }

    public Integer getSessionInvalidateTime() {
        return sessionInvalidateTime;
    }

    public void setSessionInvalidateTime(Integer sessionInvalidateTime) {
        this.sessionInvalidateTime = sessionInvalidateTime;
    }

    public Integer getSessionValidationInterval() {
        return sessionValidationInterval;
    }

    public void setSessionValidationInterval(Integer sessionValidationInterval) {
        this.sessionValidationInterval = sessionValidationInterval;
    }

    public Boolean getKaptchaOpen()
    {
        return kaptchaOpen;
    }

    public void setKaptchaOpen(Boolean kaptchaOpen)
    {
        this.kaptchaOpen = kaptchaOpen;
    }
}
