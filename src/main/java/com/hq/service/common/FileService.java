package com.hq.service.common;


import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @description:
 * @author: Mr.Huang
 * @create: 2019-04-30 16:17
 **/
public interface FileService {

    /**
     * 检查文件后缀名
     * @param extName
     * @return
     */
    Boolean checkExt(String extName);

    /**
     * 获取上传目录
     * @param extName
     * @return
     */
    String getUploadPath(String extName);

    /**
     * 本地上传
     * @param file
     * @param diskPath
     * @param uploadPath
     * @param fileName
     * @return
     */
    String fileSave(MultipartFile file, String diskPath, String uploadPath, String fileName);
    /**
     * oss存储
     * @param file
     * @param uploadPath
     * @param fileName
     * @return
     */
    String ossSave(MultipartFile file, String uploadPath, String fileName);

    /**
     * 七牛存储
     * @param file
     * @param uploadPath
     * @param fileName
     * @return
     */
    String qiniuSave(MultipartFile file, String uploadPath, String fileName);

    /**
     * 得到返回文件值
     * @param retPath
     * @param fileName
     * @return
     */
    Map<String,String> getReturnMap(String retPath, String fileName);
}
