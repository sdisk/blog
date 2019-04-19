package com.hq.controller.admin;

import com.hq.common.constant.Constants;
import com.hq.common.exception.BlogException;
import com.hq.common.rest.Result;
import com.hq.controller.BaseController;
import com.hq.model.Option;
import com.hq.service.OptionService;
import com.hq.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 设置管理
 * @author: Mr.Huang
 * @create: 2019-04-18 19:44
 **/
@Api("友链")
@Slf4j
@Controller
@RequestMapping(value = "admin/setting")
public class SettingController extends BaseController {

    @Autowired
    private OptionService optionService;

    @ApiOperation("进入设置页")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request){

        List<Option> optionList = optionService.getOptions();
        Map<String, String> optionMap = new HashMap<>();
        optionList.forEach(option -> {
            optionMap.put(option.getName(), option.getValue());
        });
        request.setAttribute("options", optionMap);
        return "admin/setting";
    }

    @ApiOperation("保存设置")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public @ResponseBody
    Result saveSettins(HttpServletRequest request){
        try{
            Map<String, String[]> paramMap = request.getParameterMap();
            Map<String, String> querys = new HashMap<>();
            paramMap.forEach((key, value) -> {
                querys.put(key, join(value));
            });
            optionService.saveOptions(querys);
            Constants.initConfig = querys;
            return ResultUtil.success();
        } catch (Exception e){
            String msg = "保存设置失败";
            if (e instanceof BlogException){
                msg = e.getMessage();
            } else {
                log.error(msg, e);
            }
            return ResultUtil.fail(e.getMessage());
        }
    }
}
