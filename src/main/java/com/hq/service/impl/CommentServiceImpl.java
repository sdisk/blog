package com.hq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hq.common.exception.BlogException;
import com.hq.common.exception.BlogExceptionEnum;
import com.hq.dao.CommentMapper;
import com.hq.dao.ContentsMapper;
import com.hq.dto.CommentQuery;
import com.hq.model.Comment;
import com.hq.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Mr.Huang
 * @create: 2019-04-02 13:13
 **/
@Service
@Slf4j
@CacheConfig(cacheNames={"blogCache"})
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ContentsMapper contentsMapper;

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
    @Cacheable(value = "commentCache",key = "'commentsByQuery_'+#p1")
    public PageInfo<Comment> getCommentsByQuery(CommentQuery commentQuery, int page, int limit) {
        if (null == commentQuery){
            throw new BlogException(BlogExceptionEnum.PARAM_IS_EMPTY);
        }
        PageHelper.startPage(page, limit);
        List<Comment> commentList = commentMapper.getCommentsByQuery(commentQuery);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentList);
        return pageInfo;
    }

    @Override
    @Cacheable(value = "commentCache",key = "'commentsByCId_'+#p0")
    public List<Comment> getCommentsByCId(Integer cid)
    {
        if (null == cid){
            throw new BlogException(BlogExceptionEnum.PARAM_IS_EMPTY);
        }
        return commentMapper.getCommentsByCid(cid);
    }
}
