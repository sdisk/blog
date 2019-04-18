package com.hq.service.impl;

import com.hq.config.QiniuConfig;
import com.hq.config.UploadConfig;
import com.hq.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: blog
 * @description:
 * @author: Mr.Huang
 * @create: 2019-04-18 16:51
 **/
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private UploadConfig uploadConfig;

    @Autowired
    private QiniuConfig qiniuConfig;
}
