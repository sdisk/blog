package com.hq.entity;

import java.util.Date;

public class Contents {
    private Integer cid;

    private String title;

    private String titlepic;

    private String slug;

    private Date createtime;

    private Date modifytime;

    private Integer authorid;

    private String type;

    private String status;

    private String tags;

    private String categories;

    private Integer hits;

    private Integer commentsnum;

    private Boolean allowcomment;

    private Boolean allowping;

    private Boolean allowfeed;

    private Boolean allowshow;

    private String content;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getTitlepic() {
        return titlepic;
    }

    public void setTitlepic(String titlepic) {
        this.titlepic = titlepic == null ? null : titlepic.trim();
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug == null ? null : slug.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public Integer getAuthorid() {
        return authorid;
    }

    public void setAuthorid(Integer authorid) {
        this.authorid = authorid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories == null ? null : categories.trim();
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getCommentsnum() {
        return commentsnum;
    }

    public void setCommentsnum(Integer commentsnum) {
        this.commentsnum = commentsnum;
    }

    public Boolean getAllowcomment() {
        return allowcomment;
    }

    public void setAllowcomment(Boolean allowcomment) {
        this.allowcomment = allowcomment;
    }

    public Boolean getAllowping() {
        return allowping;
    }

    public void setAllowping(Boolean allowping) {
        this.allowping = allowping;
    }

    public Boolean getAllowfeed() {
        return allowfeed;
    }

    public void setAllowfeed(Boolean allowfeed) {
        this.allowfeed = allowfeed;
    }

    public Boolean getAllowshow() {
        return allowshow;
    }

    public void setAllowshow(Boolean allowshow) {
        this.allowshow = allowshow;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}