package com.hq.controller.admin;

import com.hq.common.constant.Types;
import com.hq.common.exception.BlogException;
import com.hq.controller.BaseController;
import com.hq.dto.MetaQuery;
import com.hq.model.Meta;
import com.hq.service.MetaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.pattern.PathPattern;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: 文章管理
 * @author: Mr.Huang
 * @create: 2019-04-08 15:42
 **/
@Api("文章管理")
@Controller
@RequestMapping("/admin/article")
@Transactional(rollbackFor = BlogException.class)
@Slf4j
public class ArticleController extends BaseController {

    @Autowired
    private MetaService metaService;

    @Autowired
    private CacheManager cacheManager;

    @ApiOperation("发布文章页")
    @RequestMapping(value = "/publish",method = RequestMethod.GET)
    public String publish(HttpServletRequest request){
        MetaQuery metaQuery = new MetaQuery();
        metaQuery.setType(Types.CATEGORY.getType());
        List<Meta> metas = metaService.getMetas(metaQuery);
        request.setAttribute("categories", metas);
        return "admin/article_edit";
    }
}
