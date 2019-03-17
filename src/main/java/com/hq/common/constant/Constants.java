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
    private static Map<String,String> initConfig = new HashMap<>();

    /**
     * session
     */
    private static final String LOGIN_SESSION_KEY = "login_user";
    /**
     * cookie
     */
    private static final String USER_IN_COOKIE = "U_C_ID";
}
