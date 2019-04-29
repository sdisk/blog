package com.hq.controller.admin;

import com.hq.common.annotion.BussinessLog;
import com.hq.common.constant.Constants;
import com.hq.common.constant.Types;
import com.hq.common.exception.BlogException;
import com.hq.common.rest.Result;
import com.hq.controller.BaseController;
import com.hq.dto.MetaDto;
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
 * @description: 分类和标签管理
 * @author: Mr.Huang
 * @create: 2019-04-17 11:31
 **/
@Slf4j
@Controller
@RequestMapping("admin/category")
@Api("分类和标签")
public class CategoryController extends BaseController {

    @Autowired
    private MetaService metaService;

    @ApiOperation("进入分类和标签页")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        List<MetaDto> categories =  metaService.getMetaList(Types.CATEGORY.getType(), null , Constants.MAX_POSTS);
        List<MetaDto> tags = metaService.getMetaList(Types.TAG.getType(), null , Constants.MAX_POSTS);
        request.setAttribute("categories", categories);
        request.setAttribute("tags", tags);
        return "admin/category";
    }
    @ApiOperation("保存分类和标签")
    @BussinessLog("保存分类和标签")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@ApiParam(name = "cname" , value = "分类名", required = true) @RequestParam(value = "cname", required = true)String cname,
                       @ApiParam(name = "mid" , value = "meta编号", required = false) @RequestParam(value = "mid", required = false)Integer mid){
        try{
            metaService.saveMeta(Types.CATEGORY.getType(), cname, mid);
        } catch (Exception e){
            e.printStackTrace();
            String msg = "分类保存失败";
            if (e instanceof BlogException){
                BlogException ex = (BlogException)e;
                msg = ex.getMessage();
            }
            log.error(msg, e);
            return ResultUtil.fail(msg);
        }

        return ResultUtil.success();
    }

    @ApiOperation("删除分类")
    @BussinessLog("删除分类")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Result delete(@ApiParam(name = "mid", value = "主键", required = true)@RequestParam(name = "mid", required = true) Integer mid){
        try{
            metaService.deleteMetaById(mid);
        } catch (Exception e){
            e.printStackTrace();
            String msg = "删除分类失败";
            if (e instanceof BlogException){
                BlogException ex = (BlogException) e;
                msg = ex.getMessage();
            }
            log.error(msg, e);
            return ResultUtil.fail(msg);
        }
        return ResultUtil.success();

    }
}
