package com.hq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hq.common.cache.MapCache;
import com.hq.common.constant.Constants;
import com.hq.common.exception.BlogException;
import com.hq.common.exception.BlogExceptionEnum;
import com.hq.common.log.LogManager;
import com.hq.dao.CommentMapper;
import com.hq.dao.ContentsMapper;
import com.hq.dto.CommentQuery;
import com.hq.model.Comment;
import com.hq.model.Contents;
import com.hq.service.CommentService;
import com.hq.service.ContentService;
import com.hq.utils.DateUtil;
import com.hq.utils.EmailToolUtil;
import com.hq.utils.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
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

    @Autowired
    private ContentsMapper contentsMapper;

    @Autowired
    private ContentService contentService;

    @Autowired
    private EmailToolUtil emailToolUtil;

    private static final Map<String, String> STATUS_MAP = new ConcurrentHashMap<>();

    //评论正常显示
    private static final String STATUS_NORMAL = "approved";
    //评论不显示
    private static final String STATUS_BLANK = "not_audit";

    private MapCache cache = MapCache.single();

    static{
        STATUS_MAP.put("approved", STATUS_NORMAL);
        STATUS_MAP.put("not_audit", STATUS_BLANK);
    }
    @Override
    @Cacheable(value = "commentCache",key = "'commentsByQuery_'+#p0")
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "commentCache", allEntries = true)
    public void addComment(Comment comments)
    {
        String msg = null;
        if (null == comments){
            msg = "评论对象为空";
        }
        if (StringUtils.isBlank(comments.getAuthor())){
            comments.setAuthor("热心网友");
        }
        if (StringUtils.isNotBlank(comments.getMail()) && !ToolUtil.isEmail(comments.getMail())){
            msg = "请输入正确的邮箱格式";
        }
        if (StringUtils.isBlank(comments.getContent())){
            msg = "评论内容不能为空";
        }
        if (comments.getContent().length() < 5 || comments.getContent().length() > 2000) {
            msg = "评论字数在5-2000个字符";
        }
        if (null == comments.getCid()) {
            msg = "评论文章不能为空";
        }
        if (msg != null){
            throw new BlogException(400, msg);
        }
        Contents article = contentsMapper.selectByPrimaryKey(comments.getCid());
        if (null == article){
            throw new BlogException(400, "该文章不存在");
        }
        comments.setOwnerId(article.getAuthorId());
        if (0 == comments.getIsAdmin()){
            comments.setStatus(STATUS_MAP.get(STATUS_BLANK));
        } else {
            //作者评论不需要审核
            comments.setStatus(STATUS_MAP.get(STATUS_NORMAL));
        }
        comments.setCreateTime(DateUtil.getTimestampNow());
        commentMapper.addComment(comments);

        Contents temp = new Contents();
        temp.setCid(article.getCid());
        Integer count = article.getCommentsNum();
        if (null == count){
            count = 0;
        }
        temp.setCommentsNum(count + 1);
        contentService.updateArticleById(temp);
    }

    @Override
    @Cacheable(value = "commentCache", key = "'commentById_'+#p0")
    public Comment getCommentById(Integer coid) {
        if (null == coid){
            throw new BlogException(BlogExceptionEnum.PARAM_IS_EMPTY);
        }
        return commentMapper.getCommentById(coid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "commentCache", allEntries = true)
    public void deleteComment(Integer coid) {
        if (null == coid){
            throw new BlogException(BlogExceptionEnum.PARAM_IS_EMPTY);
        }
        //删除评论时同时删除子评论
        CommentQuery commentQuery = new CommentQuery();
        commentQuery.setParentId(coid);
        Comment comment = commentMapper.getCommentById(coid);
        List<Comment> commentChild = commentMapper.getCommentsByQuery(commentQuery);
        Integer count = 0 ;
        //删除子评论
        if (null != commentChild && commentChild.size() > 0){
            for (int i = 0, size = commentChild.size(); i < size; i++){
                commentMapper.deleteByPrimaryKey(commentChild.get(i).getCoid());
                count++;
            }
        }
        //删除当前评论
        commentMapper.deleteByPrimaryKey(coid);
        count++;
        //更新当前文章的评论数
        Contents contents = contentService.getArticlesById(comment.getCid());
        if (null != contents && null != contents.getCommentsNum() && contents.getCommentsNum() != 0){
            contents.setCommentsNum(contents.getCommentsNum() - count);
            contentService.updateArticleById(contents);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "commentCache", allEntries = true)
    public void updateCommentStatus(Integer coid, String status) {
        if (null == coid){
            throw new BlogException(BlogExceptionEnum.PARAM_IS_EMPTY);
        }
        commentMapper.updateCommentStatus(coid, status);

        if (STATUS_NORMAL.equals(status)){
            Comment comment = commentMapper.getCommentById(coid);
            if (StringUtils.isBlank(comment.getMail())){
                return;
            }
            LogManager.getLogManager().executeLog(new TimerTask() {
                @Override
                public void run() {
                    Contents contents = contentsMapper.selectByPrimaryKey(comment.getCid());
                    if (comment.getParentId() == 0){
                        Map<String, Object> valueMap = new HashMap<>(5);
                        valueMap.put("contentTitle", contents.getTitle());
                        valueMap.put("contentUrl", cache.get("basePath")+ "/blog/article/"+ comment.getCid());
                        valueMap.put("commentContent", comment.getContent());
                        valueMap.put("blogUrl", cache.get("blogUrl"));
                        valueMap.put("blogTitle", cache.get("blogTitle"));
                        emailToolUtil.sendTemplateMail(new String[]{comment.getMail()}, Constants.MAIL_SUBJECT_PASS, "comm/pass_mail.html", valueMap);
                    } else {
                        Comment commentPar = commentMapper.getCommentById(comment.getParentId());
                        if (StringUtils.isBlank(commentPar.getMail())){
                            return;
                        }
                        Map<String, Object> valueMap = new HashMap<>(8);
                        String url = cache.get("basePath")+ "/blog/article/"+ comment.getCid();
                        valueMap.put("commentAuthor", commentPar.getAuthor());
                        valueMap.put("contentTitle", contents.getTitle());
                        valueMap.put("contentUrl", url);
                        valueMap.put("commentContent", commentPar.getContent());
                        valueMap.put("replyAuthor", comment.getAuthor());
                        valueMap.put("replyContent", comment.getContent());
                        valueMap.put("blogUrl", cache.get("blogUrl"));
                        valueMap.put("blogTitle", cache.get("blogTitle"));
                        emailToolUtil.sendTemplateMail(new String[]{commentPar.getMail()}, Constants.MAIL_SUBJECT_REPLY, "comm/reply_mail.html", valueMap);
                    }

                }
            });
        }
    }

    @Override
    @Cacheable(value = "commentCache", key = "'allCommentsByCId_'+#p0")
    public List<Comment> getAllCommentsByCId(Integer cid) {
        List<Comment> commentList = commentMapper.getAllCommentsByCId(cid);
        return commentList;
    }
}
