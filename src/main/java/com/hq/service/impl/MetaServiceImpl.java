package com.hq.service.impl;

import com.hq.common.constant.Constants;
import com.hq.common.constant.Types;
import com.hq.common.exception.BlogException;
import com.hq.common.exception.BlogExceptionEnum;
import com.hq.dao.MetaMapper;
import com.hq.dao.RelationshipMapper;
import com.hq.dto.MetaDto;
import com.hq.dto.MetaQuery;
import com.hq.model.Contents;
import com.hq.model.Meta;
import com.hq.model.Relationship;
import com.hq.service.ContentService;
import com.hq.service.MetaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: blog
 * @description:
 * @author: Mr.Huang
 * @create: 2019-03-20 23:01
 **/
@Slf4j
@Service
@CacheConfig(cacheNames = {"blogCache"})
public class MetaServiceImpl implements MetaService {

    @Autowired
    private MetaMapper metaMapper;

    @Autowired
    private RelationshipMapper relationshipMapper;

    @Autowired
    private ContentService contentService;

    @Override
    @Cacheable(value = "metaCaches" , key = "'metaList_' + #p0")
    public List<MetaDto> getMetaList(String type, String orderby, int limit) {

        if (StringUtils.isNotBlank(type)){
            if (StringUtils.isBlank(orderby)){
                orderby = "count desc , a.mid desc ";
            }
            if (limit < 1 || limit > Constants.MAX_POSTS){
                limit = 10;
            }
            Map<String, Object> paraMap = new HashMap<>();
            paraMap.put("type", type);
            paraMap.put("order", orderby);
            paraMap.put("limit", limit);
            return metaMapper.selectMetaDtoByMap(paraMap);
        }
        return null;
    }

    @Override
    @Cacheable(value = "metaCaches",key = "'meta_'+#p0")
    public List<Meta> getMetas(MetaQuery metaQuery) {
        return metaMapper.getMetasByMetaQuery(metaQuery);
    }

    @Override
    @CacheEvict(value = {"metaCaches","metaCache"},allEntries = true,beforeInvocation = true)
    public void addMetas(Integer cid, String names, String type) {
        if (null == cid){
            throw new BlogException(BlogExceptionEnum.PARAM_IS_EMPTY);
        }
        if (StringUtils.isNotBlank(names) && StringUtils.isNotBlank(type)){
            String [] nameArr = StringUtils.split(names, ",");
            for (String name : nameArr){
                this.saveOrUpdate(cid, name, type);
            }
        }

    }
    @Override
    @CacheEvict(value = {"metaCaches","metaCache"},allEntries = true,beforeInvocation = true)
    public void saveOrUpdate(Integer cid, String name, String type) {
        MetaQuery metaQuery = new MetaQuery();
        metaQuery.setName(name);
        metaQuery.setType(type);
        List<Meta> metas = this.getMetas(metaQuery);
        int mid;
        Meta meta;
        if (metas.size() == 1){
            Meta meta1 = metas.get(0);
            mid = meta1.getMid();
        } else if (metas.size() > 1){
            throw new BlogException(BlogExceptionEnum.META_NOT_ONE_RESULT);
        } else {
            //save
            meta = new  Meta();
            meta.setSlug(name);
            meta.setName(name);
            meta.setType(type);
            this.addMeta(meta);
            mid = meta.getMid();
        }
        if (mid != 0){
            Long count = relationshipMapper.getCountById(cid, mid);
            if (count == 0){
                Relationship relationship = new Relationship();
                relationship.setCid(cid);
                relationship.setMid(mid);
                relationshipMapper.insert(relationship);
            }
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"metaCaches","metaCache"},allEntries = true,beforeInvocation = true)
    public void deleteMetaById(Integer mid) {
        if (null == mid){
            throw new BlogException(BlogExceptionEnum.PARAM_IS_EMPTY);
        }
        Meta meta = metaMapper.getMetaById(mid);
        if (null != meta){
            String type = meta.getType();
            String name = meta.getName();
            metaMapper.deleteById(mid);
            //删除关联表数据
            List<Relationship> relationships = relationshipMapper.getRelationShipByMid(mid);
            if (null != relationships && relationships.size() > 0){
                for (Relationship re: relationships){
                    Contents article = contentService.getArticlesById(re.getCid());
                    if (null != article){
                        Contents temp = new Contents();
                        temp.setCid(re.getCid());
                        if (type.equals(Types.CATEGORY.getType())){
                            temp.setCategories(reMeta(name, article.getCategories()));
                        }
                        if (type.equals(Types.TAG.getType())){
                            temp.setTags(reMeta(name, article.getTags()));
                        }
                        //删除相关项目资源
                        contentService.updateArticleById(temp);
                    }
                }
                relationshipMapper.deleteByMid(mid);
            }
        }
    }



    @Override
    @Transactional
    @CacheEvict(value = {"metaCaches","metaCache"},allEntries = true,beforeInvocation = true)
    public void updateMeta(Meta meta) {
        if (null == meta || null == meta.getMid()){
            throw new BlogException(BlogExceptionEnum.PARAM_IS_EMPTY);
        }
        metaMapper.updateMeta(meta);
    }

    @Override
    @Cacheable(value = "metaCache", key = "'metaById_'+#p0")
    public Meta getMetaById(Integer mid) {
        if (null == mid){
            throw new BlogException(BlogExceptionEnum.PARAM_IS_EMPTY);
        }
        return metaMapper.getMetaById(mid);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"metaCache", "metaCaches"}, allEntries = true, beforeInvocation = true)
    public void addMeta(Meta meta) {
        if (null == meta){
            throw new BlogException(BlogExceptionEnum.PARAM_IS_EMPTY);
        }
        metaMapper.insert(meta);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"metaCache", "metaCaches"}, allEntries = true, beforeInvocation = true)
    public void saveMeta(String type, String name, Integer mid) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(name)){
            MetaQuery metaQuery = new MetaQuery();
            metaQuery.setName(name);
            metaQuery.setType(type);
            List<Meta> metas = metaMapper.getMetasByMetaQuery(metaQuery);
            if (null != metas && metas.size() > 0){
                Meta domain = new Meta();
                domain.setName(name);
                if (null != mid){
                    Meta meta = metaMapper.getMetaById(mid);
                    if (null != meta){
                        domain.setMid(mid);
                    }
                    metaMapper.updateMeta(meta);
                    //更新原有的文章分类
                    contentService.updateCategory(meta.getName(), name);
                } else {
                    domain.setType(type);
                    metaMapper.addMeta(domain);
                }
            } else {
                throw new BlogException(BlogExceptionEnum.META_IS_EXIST);
            }
        }
    }

    private String reMeta(String name, String metas) {
        String []  ms = StringUtils.split(metas, ",");
        StringBuffer sb = new StringBuffer();
        for (String m : ms){
            if (!name.equals(m)){
                sb.append(",").append(m);
            }
        }
        if (sb.length() > 0){
            return sb.substring(1);
        }
        return "";
    }
}
