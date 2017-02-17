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
    @Column(name = "position")
    private String position; // 广告位位置：homePage、listPage、detailPage
    @Column(name = "type")
    private String type; // 广告位类型：alliance、loopImg、oneImg
    @Column(name = "link_type")
    private String linkType; // 广告链接类型，如果不是广告联盟的广告有此属性：innerLink、outerLink
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
