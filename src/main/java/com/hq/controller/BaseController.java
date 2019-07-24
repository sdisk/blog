package com.hq.controller;

import com.hq.common.cache.MapCache;
import com.hq.common.constant.Constants;
import com.hq.common.constant.Types;
import com.hq.dto.ContentQuery;
import com.hq.dto.MetaDto;
import com.hq.model.User;
import com.hq.service.ContentService;
import com.hq.service.MetaService;
import com.hq.utils.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @description:
 * @create: 2019-03-20 22:53
 **/
public abstract class BaseController {


    @Autowired
    private MetaService metaService;

    protected MapCache cache = MapCache.single();

    public BaseController title(HttpServletRequest request,String title){
        request.setAttribute("title", title);
        return this;
    }
    /**
     * 获取登录对象
     */
    public User getLoginUser(HttpServletRequest request){
        return ToolUtil.getLoginUser(request);
    }
    /**
     * 获取登录的uid
     */
    public Integer getUid(HttpServletRequest request){
        return this.getLoginUser(request).getUid();
    }
    /**
     * 获取博客页面需要的公共数据
     */
    public BaseController blogBaseData(HttpServletRequest request, ContentQuery contentQuery){

        List<MetaDto> links = metaService.getMetaList(Types.LINK.getType(), null, Constants.MAX_POSTS);

        request.setAttribute("links", links);

        return this;
    }
    /**
     * 获得session
     */
    public HttpSession getSession(HttpServletRequest request){
        return request.getSession(true);
    }

    /**
     * 数组转字符串
     *
     * @param arr
     * @return
     */
    public String join(String[] arr) {
        StringBuilder ret = new StringBuilder();
        String[] var3 = arr;
        int var4 = arr.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String item = var3[var5];
            ret.append(',').append(item);
        }

        return ret.length() > 0 ? ret.substring(1) : ret.toString();
    }
}
