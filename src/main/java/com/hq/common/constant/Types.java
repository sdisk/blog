package com.hq.common.constant;

/**
 * @author: Mr.Huang
 * @create: 2019-03-20 10:34
 **/
public enum Types {

    TAG("tag"),
    CATEGORY("category"),
    ARTICLE("post"),
    PUBLISH("publish"),
    PAGE("page"),
    DRAFT("draft"),
    LINK("link"),
    IMAGE("image"),
    FILE("file"),
    CSRF_TOKEN("csrf_token"),
    COMMENTS_FREQUENCY("comments:frequency"),
    PHOTO("photo"),

    /**
     * 附件存放的URL，默认为网站地址，可为第三方CDN域名
     */
    ATTACH_URL("attach_url"),
    /**
     * 网站过滤的禁止访问ip
     */
    BLOCK_IPS("site_block_ips");

    private String type;

    Types(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
