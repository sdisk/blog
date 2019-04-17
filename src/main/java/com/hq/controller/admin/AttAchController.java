package com.hq.controller.admin;

import com.github.pagehelper.PageInfo;
import com.hq.common.constant.Constants;
import com.hq.common.constant.Types;
import com.hq.controller.BaseController;
import com.hq.dto.AttachDto;
import com.hq.service.AttachService;
import com.hq.utils.Commons;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 附件管理
 * @author: Mr.Huang
 * @create: 2019-04-17 16:31
 **/
@Api("附件管理")
@Controller
@RequestMapping("admin/attach")
@Slf4j
public class AttAchController extends BaseController {

    @Autowired
    private AttachService attachService;

    @ApiOperation("文件管理首页")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(@ApiParam(name = "page", value = "页数", required = false)
                            @RequestParam(name = "page", required = false, defaultValue = "1")int page,
            @ApiParam(name="limit",value = "条数",required = false)
            @RequestParam(name = "limit",required = false,defaultValue = "10")int limit, HttpServletRequest request){
        PageInfo<AttachDto> atts = attachService.getAtts(page, limit);
        request.setAttribute("attachs", atts);
        request.setAttribute(Types.ATTACH_URL.getType(), Commons.site_option(Types.ATTACH_URL.getType(), Commons.site_url()));
        request.setAttribute("max_file_size", Constants.MAX_FILE_SIZE / 1024);
        return "admin/attach";
    }
}
