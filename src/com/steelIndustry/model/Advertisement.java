package com.steelIndustry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.steelIndustry.framework.base.BaseSimplePojo;

@Entity
@Table(name = "advertisement")
@NamedQuery(name = "Advertisement.findAll", query = "SELECT t FROM Advertisement t")
public class Advertisement implements BaseSimplePojo {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;// 主键
    @Column(name = "title")
    private String title; // 广告标题
    @Column(name = "link_type")
    private String linkType; // 广告链接类型，如果不是广告联盟的广告有此属性：innerLink、outerLink
    @Column(name = "img")
    private String img; // 图片名称
    @Column(name = "url")
    private String url; // 外部链的url
    @Column(name = "store_id")
    private String storeId; // 内部链接店铺id
    @Column(name = "content")
    private String content; // 广告内容，广告联盟的话为广告联盟所需信息的json字符串，内部链接为店铺id，外部链接为url

    public Advertisement() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
