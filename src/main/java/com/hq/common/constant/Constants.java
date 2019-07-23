package com.hq.common.constant;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huang on 17/3/2019.
 */
@Component
public class Constants
{
    /**
     * 网站配置
     */
    public static Map<String, String> initConfig = new HashMap<>();

    /**
     * session
     */
    public static final String LOGIN_SESSION_KEY = "login_user";
    /**
     * captcha
     */
    public static final String CAPTCHA_SESSION_KEY = "CAPTCHA_SESSION_KEY";
    /**
     * cookie
     */
    public static final String USER_IN_COOKIE = "U_C_ID";
    /**
     * aes加密加盐
     */
    public static final String AES_SALT = "123456789abcdefghijklmnpqrstuvwxyz";
    /**
     * 用户密码加密加盐
     */
    public static final String USER_SALT = "135792468abcdefgh";

    /**
     * 密码错误次数
     */
    public static final int RETRY_TIME = 3;

    /**
     * 最大获取文章条数
     */
    public static final int MAX_POSTS = 9999;

    /**
     * 最大页码
     */
    public static final int MAX_PAGE = 100;

    /**
     * 文章最多可以输入的文字数
     */
    public static final int MAX_TEXT_COUNT = 200000;

    /**
     * 文章标题最多可以输入的文字个数
     */
    public static final int MAX_TITLE_COUNT = 200;

    /**
     * 点击次数超过多少更新到数据库
     */
    public static final int HIT_EXCEED = 10;

    /**
     * 上传文件最大1M
     */
    public static Integer MAX_FILE_SIZE = 1048576;
}
