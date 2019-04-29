package com.hq.controller.admin;

import com.hq.common.annotion.BussinessLog;
import com.hq.common.constant.Types;
import com.hq.common.rest.Result;
import com.hq.controller.BaseController;
import com.hq.dto.MetaQuery;
import com.hq.model.Meta;
import com.hq.service.MetaService;
import com.hq.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: 友链管理
 * @author: Mr.Huang
 * @create: 2019-04-18 18:21
 **/
@Api("友链")
@Slf4j
@Controller
@RequestMapping(value = "admin/link")
public class LinksController extends BaseController {

    @Autowired
    private MetaService metaService;

    @ApiOperation("友链界面")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        MetaQuery metaQuery = new MetaQuery();
        metaQuery.setType(Types.LINK.getType());
        List<Meta> metas = metaService.getMetas(metaQuery);
        request.setAttribute("links", metas);
        return "admin/links";
    }

    @ApiOperation("新增友链")
    @BussinessLog("新增友链")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public @ResponseBody
    Result saveLink(@ApiParam(name = "title", value = "标签", required = true)
                    @RequestParam(name = "title", required = true)String title,
                    @ApiParam(name = "url", value = "链接", required = true)
                    @RequestParam(name = "url", required = true)String url,
                    @ApiParam(name = "logo", value = "logo", required = false)
                    @RequestParam(name = "logo", required = false)String logo,
                    @ApiParam(name = "sort", value = "sort", required = false)
                    @RequestParam(name = "sort", required = false, defaultValue = "0")int sort,
                    @ApiParam(name = "mid", value = "编号", required = false)
                    @RequestParam(name = "mid", required = false)Integer mid){
        Meta meta = new Meta();
        meta.setName(title);
        meta.setSlug(url);
        meta.setDescription(logo);
        meta.setSort(sort);
        meta.setType(Types.LINK.getType());
        if (null != mid){
            meta.setMid(mid);
            metaService.updateMeta(meta);
        } else {
            metaService.addMeta(meta);
        }
        return ResultUtil.success();
    }

    @ApiOperation("删除友链")
    @BussinessLog("删除友链")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public @ResponseBody Result delete(@ApiParam(name = "mid",value = "meta主键",required = true)
                                       @RequestParam(name = "mid",required = true)int mid
                                       ){
        metaService.deleteMetaById(mid);
        return ResultUtil.success();
    }
}
