package com.hq.controller.admin;

import com.github.pagehelper.PageInfo;
import com.hq.common.constant.Constants;
import com.hq.common.constant.Types;
import com.hq.common.exception.BlogException;
import com.hq.common.exception.BlogExceptionEnum;
import com.hq.common.rest.Result;
import com.hq.config.UploadConfig;
import com.hq.controller.BaseController;
import com.hq.dto.AttachDto;
import com.hq.model.Attach;
import com.hq.model.User;
import com.hq.service.AttachService;
import com.hq.service.common.FileService;
import com.hq.utils.Commons;
import com.hq.utils.ResultUtil;
import com.hq.utils.ToolUtil;
import com.hq.utils.UUID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

/**
 * @description: 附件管理
 * @author: Mr.Huang
 * @create: 2019-04-17 16:31
 **/
@Api("附件管理")
@Controller
@RequestMapping("/admin/attach")
@Slf4j
public class AttAchController extends BaseController {

    @Autowired
    private AttachService attachService;

    @Autowired
    private FileService fileService;

    @Autowired
    private UploadConfig uploadConfig;

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

    @ApiOperation("文件上传")
    @RequestMapping(value = "upload", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody Result fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        try {
            if (!file.isEmpty()) {
                if (StringUtils.isEmpty(uploadConfig.getHardDisk()) && "local".equals(uploadConfig.getUpType())) {
                    return ResultUtil.fail("请配置上传目录");
                }
                String diskPath = uploadConfig.getHardDisk();
                //扩展名格式
                String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                //验证文件类型
                if (!fileService.checkExt(extName)) {
                    return ResultUtil.fail("上传文件格式不支持");
                }
                //根据文件类型获取上传目录
                String uploadPath = fileService.getUploadPath(extName);
                uploadPath = uploadPath.replace(File.separator, "/");
                if (StringUtils.isEmpty(uploadPath)) {
                    return ResultUtil.fail("上传文件路径错误");
                }
                String fileName = UUID.getUUID() + extName;
                String retPath = "";
                if ("local".equals(uploadConfig.getUpType()) && StringUtils.isNotEmpty(uploadConfig.getUpType())) {
                    retPath = fileService.fileSave(file, diskPath, uploadPath, fileName);
                } else if ("oss".equals(uploadConfig.getUpType())) {
                    retPath = fileService.ossSave(file, uploadPath, fileName);
                } else if ("qiniu".equals(uploadConfig.getUpType())) {
                    retPath = fileService.qiniuSave(file, uploadPath, fileName);
                }
                if ("null".equals(retPath)) {
                    return ResultUtil.fail("上传文件异常");
                }
                Map<String, String> upMap = fileService.getReturnMap(retPath, fileName);

                Attach attach = new Attach();
                HttpSession session = request.getSession();
                User sessionUser = (User) session.getAttribute(Constants.LOGIN_SESSION_KEY);
                attach.setCreatorId(sessionUser.getUid());
                attach.setFtype(ToolUtil.isImage(file.getInputStream()) ? Types.IMAGE.getType() : Types.FILE.getType());
                attach.setFname(fileName);
                attach.setFkey(upMap.get("previewUrl"));
                attach.setCreateTime(new Timestamp(System.currentTimeMillis()));
                attachService.addAttAch(attach);
                return ResultUtil.success();
            } else {
                log.error("File cannot be empty!");
                return ResultUtil.fail("File cannot be empty!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BlogException(BlogExceptionEnum.UPLOAD_FILE_FAIL);
        }
    }
    @ApiOperation("删除文件记录")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteFileInfo(
            @ApiParam(name = "id", value = "文件主键", required = true)
            @RequestParam(name = "id", required = true)
                    Integer id,
            HttpServletRequest request
    ){
        try {
            AttachDto attAch = attachService.getAttAchById(id);
            if (null == attAch){
                throw new BlogException(BlogExceptionEnum.FILE_NOT_FOUND);
            }
            attachService.deleteAttAch(id);
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BlogException(BlogExceptionEnum.ATTACH_DELETE_FAIL);
        }
    }
}
