package com.hq.controller.admin;

import com.google.code.kaptcha.Producer;
import com.hq.common.annotion.BussinessLog;
import com.hq.common.constant.Constants;
import com.hq.common.rest.Result;
import com.hq.config.BlogProperties;
import com.hq.controller.BaseController;
import com.hq.dto.StatisticsDto;
import com.hq.model.Comment;
import com.hq.model.Contents;
import com.hq.model.OperationLog;
import com.hq.model.User;
import com.hq.service.OperationLogService;
import com.hq.service.SiteService;
import com.hq.service.UserService;
import com.hq.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

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

    private static final int COMMENT_NUM = 5;

    private static final int ARTICLE_NUM = 5;

    private static final int LOG_NUM = 5;

    @Autowired
    private SiteService siteService;

    @Autowired
    private UserService userService;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private BlogProperties blogProperties;

    @Autowired
    private Producer producer;



    @ApiOperation("进入首页")
    @RequestMapping(value = {"", "/index"},method = RequestMethod.GET)
    @BussinessLog("访问首页")
    public String index(HttpServletRequest request){
        List<Comment> comments = siteService.getComments(COMMENT_NUM);
        List<Contents> contents = siteService.getArticles(ARTICLE_NUM);
        StatisticsDto statisticsDto = siteService.getStatistics();
        List<OperationLog> logs = operationLogService.getLogs(LOG_NUM);

        request.setAttribute("comments", comments);
        request.setAttribute("contents", contents);
        request.setAttribute("statistics", statisticsDto);
        request.setAttribute("logs", logs);
        return "admin/index";
    }

    @ApiOperation("个人设置页面")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(){
        return "admin/profile";
    }

    @ApiOperation("保存个人信息")
    @RequestMapping(value = "saveProfile", method = RequestMethod.POST)
    @BussinessLog("保存个人信息")
    public @ResponseBody
    Result saveProfile(@ApiParam(name = "srceenName" , value = "姓名", required = true) @RequestParam String srceenName,
                       @ApiParam(name = "email", value = "邮箱", required = true) @RequestParam String email, HttpServletRequest request){
        User user = super.getLoginUser(request);
        if (StringUtils.isNotBlank(srceenName) && StringUtils.isNotBlank(email)){
            User temp = new User();
            temp.setUid(user.getUid());
            temp.setScreenName(srceenName);
            temp.setEmail(email);
            userService.updateUser(temp);
            //更新session中数据
            HttpSession session = super.getSession(request);
            User originalUser = (User) session.getAttribute(Constants.LOGIN_SESSION_KEY);
            originalUser.setScreenName(srceenName);
            originalUser.setEmail(email);
            session.setAttribute(Constants.LOGIN_SESSION_KEY, originalUser);
        }
        return ResultUtil.success();
    }
    /**
     * 验证码
     */
    @ApiOperation("获取验证码")
    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void captcha(HttpServletRequest request, HttpServletResponse response){
        if (blogProperties.getKaptchaOpen()){
            HttpSession session = request.getSession();

            //set expires
            response.setDateHeader("Expires", 0);

            // Set standard HTTP/1.1 no-cache headers.
            response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");

            // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");

            // Set standard HTTP/1.0 no-cache header.
            response.setHeader("Pragma", "no-cache");

            // return a jpeg
            response.setContentType("image/jpeg");

            // create the text for the image
            String capText = producer.createText();

            // store the text in the session
            session.setAttribute(Constants.CAPTCHA_SESSION_KEY, capText);

            // create the image with the text
            BufferedImage bi = producer.createImage(capText);

            ServletOutputStream os = null;
            try {
                os = response.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // write the data out
            try {
                ImageIO.write(bi, "jpg", os);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // data flush
            try {
                try {
                    os.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } finally {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
