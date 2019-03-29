package com.hq.controller.admin;

import com.github.pagehelper.PageInfo;
import com.hq.common.constant.Constants;
import com.hq.common.constant.Types;
import com.hq.controller.BaseController;
import com.hq.dto.ContentQuery;
import com.hq.model.Content;
import com.hq.service.ContentService;
import com.hq.service.OptionsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 首页
 * @author: Mr.Huang
 * @create: 2019-03-23 18:21
 **/
@Controller
@Api("网站后台首页")
@RequestMapping("/admin")
@Slf4j
public class IndexController extends BaseController {


}
