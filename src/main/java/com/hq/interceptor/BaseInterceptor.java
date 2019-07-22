package com.hq.interceptor;

import com.hq.common.cache.MapCache;
import com.hq.common.constant.Constants;
import com.hq.common.constant.Types;
import com.hq.model.Option;
import com.hq.model.User;
import com.hq.service.OptionService;
import com.hq.service.UserService;
import com.hq.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huang on 19/3/2019.
 */
@Component
@Slf4j
public class BaseInterceptor implements HandlerInterceptor
{
    private static final String USER_AGENT = "user-agent";
    private static final String WEB_CSRF_TOKEN = "_csrf_token";
    private static final String WEB_SITE_RECORD = "site_record";
    private static final String WEB_COMMONS = "commons";
    private static final String WEB_OPTION = "option";
    private static final String WEB_ADMINCOMMONS = "adminCommons";
    //30分钟
    private static final long EXPIRED =  1800;

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
        String uri = request.getRequestURI();
        log.info("UserAgent: {}", request.getHeader(USER_AGENT));
        log.info("用户访问地址: {}, 来路地址: {}", uri, IPUtil.getIpAddrByRequest(request));

        //请求拦截
        User user = ToolUtil.getLoginUser(request);
        if (null == user){
            Integer uid = ToolUtil.getCookieUid(request);
            if (null != uid){
                user = userService.getUserInfoById(uid);
                request.getSession().setAttribute(Constants.LOGIN_SESSION_KEY, user);
            }
        }

        //对需要后台登录的地址排除
        if (uri.startsWith("/admin") && !uri.startsWith("/admin/login") && null == user
                && !uri.startsWith("/admin/admin") && !uri.startsWith("/admin/images")
                && !uri.startsWith("/admin/css") && !uri.startsWith("/admin/js")
                && !uri.startsWith("/admin/error") && !uri.startsWith("/admin/plugins")
                && !uri.startsWith("/admin/editormd")){
            //重定向到登录页
            if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
                //如果request.getHeader("X-Requested-With") 返回的是"XMLHttpRequest"说明就是ajax请求\
                response.setHeader("REDIRECT", "REDIRECT");
                //获取当前请求的路径
                String basePath = request.getScheme() + "://" + request.getServerName() + ":"  + request.getServerPort()+request.getContextPath();
                response.setHeader("CONTENTPATH", basePath+"/admin/login");
                return false;
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/login");
                return false;
            }

        }
        //设置get请求的token
        if (request.getMethod().equals("GET")){
            String csrf_token = UUID.UU64();
            //存放到缓存中
            cache.hset(Types.CSRF_TOKEN.getType(), csrf_token , uri , EXPIRED);
            request.setAttribute(WEB_CSRF_TOKEN, csrf_token);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object
            handler, ModelAndView modelAndView) throws Exception
    {
        Option op = optionService.getOptionByName(WEB_SITE_RECORD);
        //将公用方法给前端
        request.setAttribute(WEB_COMMONS, commons);
        request.setAttribute(WEB_OPTION, op);
        request.setAttribute(WEB_ADMINCOMMONS , adminCommons);
        initSiteConfig(request);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object
            handler, Exception ex) throws Exception
    {

    }
    private void initSiteConfig(HttpServletRequest request){
        if (Constants.initConfig.isEmpty()){
            List<Option> options = optionService.getOptions();
            Map<String, String> querys = new HashMap<>();
            options.forEach(option -> {
                querys.put(option.getName(), option.getValue());
            });
            Constants.initConfig = querys;
        }
    }
}
