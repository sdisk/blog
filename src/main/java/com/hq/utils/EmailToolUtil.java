package com.hq.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @description: 邮件工具类
 * @author: Mr.Huang
 * @create: 2019-07-29 17:24
 **/
@Slf4j
@Component
public class EmailToolUtil {

    @Value("${spring.mail.username}")
    private String address;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 邮箱验证正则
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    public void sendTemplateMail(String [] to, String subject, String template, Map<String, Object> valueMap) {
        MimeMessage mimeMessage = null;
        if (!Pattern.matches(REGEX_EMAIL, address)){
            log.error("发送邮件地址配置不符合邮箱规范，发送地址：{}", address);
            return;
        }
        for (String str : to){
            if (!Pattern.matches(REGEX_EMAIL, str)){
                log.error("接收邮件地址配置不符合邮箱规范，接收地址：{}", str);
                return;
            }
        }
        try {
            mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            // 设置发件人邮箱
            helper.setFrom(address);
            // 设置收件人邮箱
            helper.setTo(to);
            // 设置邮件标题
            helper.setSubject(subject);

            // 添加正文（使用thymeleaf模板）
            Context context = new Context();
            //设置内容变量
            context.setVariables(valueMap);
            String content = this.templateEngine.process(template, context);
            helper.setText(content, true);

            // 添加附件
            if (valueMap.get("filePathList") != null) {
                String[] filePathList = (String[]) valueMap.get("filePathList");
                for (String filePath : filePathList) {
                    FileSystemResource fileSystemResource = new FileSystemResource(new File(filePath));
                    String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
                    helper.addAttachment(fileName, fileSystemResource);
                }
            }
            // 发送邮件
            mailSender.send(mimeMessage);
            log.info("send mail to:{}", Arrays.toString(to));
        } catch (MessagingException e) {
            log.error("send mail fail", e);
            e.printStackTrace();
        }
    }
}
