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
import java.util.List;

/**
 * @description:
 * @create: 2019-03-20 22:53
 **/
public abstract class BaseController {

    @Autowired
    private ContentService contentService;
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
}
