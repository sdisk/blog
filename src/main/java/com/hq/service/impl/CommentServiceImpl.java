package com.hq.service.impl;

import com.github.pagehelper.PageInfo;
import com.hq.dao.CommentMapper;
import com.hq.dto.CommentQuery;
import com.hq.model.Comment;
import com.hq.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Mr.Huang
 * @create: 2019-04-02 13:13
 **/
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    private static final Map<String, String> STATUS_MAP = new ConcurrentHashMap<>();

    //评论正常显示
    private static final String STATUS_NORMAL = "approved";
    //评论不显示
    private static final String STATUS_BLANK = "not_audit";

    static{
        STATUS_MAP.put("approved", STATUS_NORMAL);
        STATUS_MAP.put("not_audit", STATUS_BLANK);
    }
    @Override
    public PageInfo<Comment> getCommentsByQuery(CommentQuery commentQuery, int page, int limit) {

        return null;
    }
}
