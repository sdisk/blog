package com.hq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hq.dao.CommentMapper;
import com.hq.model.Comment;
import com.hq.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * @author: Mr.Huang
 * @create: 2019-04-02 13:13
 **/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
}
