package com.hq.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 上传基础配置
 * @author: Mr.Huang
 * @create: 2019-04-18 15:07
 **/
@Configuration
@Getter
@Setter
public class UploadConfig {

    /**
     * 上传方式
     */
    @Value("${upload.config.up-type}")
    private String upType;

    /**
     * 图片访问地址
     */
    @Value("upload.config.up-cdn")
    private String upCdn;

    /**
     * 单个文件上传大小，单位为M
     */
    @Value("${upload.config.max-file-size}")
    private Integer maxFileSize;

    /**
     * 路径
     */
    @Value("${upload.config.hard-disk}")
    private String hardDisk;

    /**
     * 默认图片类型，上传文件夹
     */
    @Value("${upload.config.image-folder}")
    private String imageFolder;

    /**
     * 文档类型上传文件夹
     */
    @Value("${upload.config.document-folder}")
    private String documentFolder;

    /**
     * 视频类型，上传文件夹
     */
    @Value("${upload.config.video-folder}")
    private String videoFolder;

    /**
     * 音频类型，上传文件夹
     */
    @Value("${upload.config.music-folder}")
    private String musicFolder;

    /**
     * 允许上传的图片类型
     */
    @Value("${upload.config.image-type}")
    private String imageType;

    /**
     * 允许上传的文档类型
     */
    @Value("${upload.config.document-type}")
    private String documentType;

    /**
     * 允许上传的视频类型
     */
    @Value("${upload.config.video-type}")
    private String videoType;

    /**
     * 允许上传的音频类型
     */
    @Value("${upload.config.music-type}")
    private String musicType;

    /**
     * 允许上传的ip
     *//*
    @Value("${upload.config.allow-ip}")
    private String allowIp;*/
}
