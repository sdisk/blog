package com.hq.interceptor;

import com.hq.common.cache.MapCache;
import com.hq.service.OptionService;
import com.hq.service.UserService;
import com.hq.utils.AdminCommons;
import com.hq.utils.Commons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huang on 19/3/2019.
 */
@Component
@Slf4j
public class BaseInterceptor implements HandlerInterceptor
{
    private static final String USER_AGENT = "user-agent";

    @Autowired
    private UserService userService;

    @Autowired
    private OptionService optionService;

    @Autowired
    private Commons commons;

    @Autowired
    private AdminCommons adminCommons;

    private MapCache cache = MapCache.single();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object
            handler) throws Exception
    {
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object
            handler, ModelAndView modelAndView) throws Exception
    {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object
            handler, Exception ex) throws Exception
    {

    }
}
