package com.hq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hq.common.constant.Constants;
import com.hq.common.constant.Types;
import com.hq.common.exception.BlogException;
import com.hq.common.exception.BlogExceptionEnum;
import com.hq.dao.CommentMapper;
import com.hq.dao.ContentsMapper;
import com.hq.dao.MetaMapper;
import com.hq.dao.RelationshipMapper;
import com.hq.dto.ContentQuery;
import com.hq.model.Comment;
import com.hq.model.Contents;
import com.hq.model.Relationship;
import com.hq.service.ContentService;
import com.hq.service.MetaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 *
 * @author: Mr.Huang
 * @create: 2019-03-20 22:59
 **/
@Slf4j
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentsMapper contentsMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private MetaService metaService;

    @Autowired
    private RelationshipMapper relationshipMapper;

    @Autowired
    private CacheManager cacheManager;

    @Override
    @Cacheable(value = "articleCaches", key = "'articlesByQuery_' + #p1 +'type_'+#p0.type")
    public PageInfo<Contents> getArticlesByQuery(ContentQuery query, int p, int limit) {
        if (null == query){
            throw new BlogException(BlogExceptionEnum.PARAM_IS_EMPTY);
        }
        PageHelper.startPage(p, limit);
        List<Contents> contents = contentsMapper.getContentsByQuery(query);
        PageInfo<Contents> pageInfo = new PageInfo<>(contents);
        return pageInfo;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"articleCache", "articleCaches"}, allEntries = true, beforeInvocation =
            true)
    public void deleteArticlesById(Integer cid)
    {
        if (null == cid){
            throw new BlogException(BlogExceptionEnum.PARAM_IS_EMPTY);
        }
        contentsMapper.deleteByPrimaryKey(cid);
        //删除文章也要删除相关的评论
        List<Comment> comments = commentMapper.getCommentsByCid(cid);
        if (null != comments && comments.size() > 0){
            comments.forEach(comment -> {
                commentMapper.deleteByPrimaryKey(comment.getCoid());
            });
        }
        //删除关联的标签和分类
        List<Relationship> relationships = relationshipMapper.getRelationShipByCid(cid);
        if (null != relationships && relationships.size() > 0){
            relationshipMapper.getRelationShipByCid(cid);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"articleCache","articleCaches"},allEntries = true,beforeInvocation = true)
    public void save(Contents contents)
    {
        if (null == contents){
            throw new BlogException(BlogExceptionEnum.PARAM_IS_EMPTY);
        }
        if (StringUtils.isBlank(contents.getTitle())){
            throw new BlogException(BlogExceptionEnum.TITLE_CAN_NOT_EMPTY);
        }
        if (contents.getTitle().length() > Constants.MAX_TITLE_COUNT){
            throw new BlogException(BlogExceptionEnum.TITLE_IS_TOO_LONG);
        }
        if (StringUtils.isBlank(contents.getContent())){
            throw new BlogException(BlogExceptionEnum.CONTENT_CAN_NOT_EMPTY);
        }
        if (contents.getContent().length() > Constants.MAX_TEXT_COUNT){
            throw new BlogException(BlogExceptionEnum.CONTENT_IS_TOO_LONG);
        }

        //标签和分类
        String tags = contents.getTags();
        String categories = contents.getCategories();

        contentsMapper.insert(contents);

        int cid = contents.getCid();
        metaService.addMetas(cid, tags, Types.TAG.getType());
        metaService.addMetas(cid, categories, Types.CATEGORY.getType());

    }

    @Override
    @Cacheable(value = "articleCaches", key = "'ArticlesById_'+#p0")
    public Contents getArticlesById(Integer cid)
    {
        if (null == cid){
            throw new BlogException(BlogExceptionEnum.PARAM_IS_EMPTY);
        }
        return contentsMapper.selectByPrimaryKey(cid);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"articleCache","articleCaches"},allEntries = true,beforeInvocation = true)
    public void updateArticleById(Contents contents)
    {
        //标签和分类
        String tags = contents.getTags();
        String categories = contents.getCategories();

        contentsMapper.updateArticleById(contents);
        int cid = contents.getCid();
        relationshipMapper.deleteByCid(cid);
        metaService.addMetas(cid, tags , Types.TAG.getType());
        metaService.addMetas(cid, categories, Types.CATEGORY.getType());
    }

    @Override
    @Transactional
    @CacheEvict(value = {"articleCache","articleCaches"},allEntries = true,beforeInvocation = true)
    public void updateCategory(String ordinal, String newCatefory) {
        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setCategory(ordinal);
        List<Contents> atricles = contentsMapper.getContentsByQuery(contentQuery);
        atricles.forEach(atricle -> {
            atricle.setCategories(atricle.getCategories().replace(ordinal, newCatefory));
            contentsMapper.updateArticleById(atricle);
        });
    }

    @Override
    public PageInfo<Contents> searchArticle(String param, int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<Contents> contentsList = contentsMapper.searchArticle(param);
        PageInfo<Contents> pageInfo = new PageInfo<>(contentsList);
        return pageInfo;
    }
}
