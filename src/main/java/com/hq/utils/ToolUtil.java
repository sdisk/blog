package com.hq.utils;

/**
 * @description: 工具类
 * @author: Mr.Huang
 * @create: 2019-03-18 17:01
 **/
public class ToolUtil {

    /**
     * 获取临时目录
     */
    public static String getTempPath(){
        return System.getProperty("java.io.tmpdir");
    }

}
