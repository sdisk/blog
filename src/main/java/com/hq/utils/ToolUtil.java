package com.hq.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

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

    /**
     * 获取异常信息
     * @param e
     * @return
     */
    public static String getExceptionMsg(Exception e) {
        StringWriter sw = new StringWriter();
        try {
            e.printStackTrace(new PrintWriter(sw));
        } finally {
            try {
                sw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return sw.getBuffer().toString().replaceAll("\\$", "T");
    }

}
