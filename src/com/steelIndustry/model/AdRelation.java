package com.steelIndustry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.steelIndustry.framework.base.BaseSimplePojo;

@Entity
@Table(name = "ad_relation")
@NamedQuery(name = "AdRelation.findAll", query = "SELECT t FROM AdRelation t")
public class AdRelation implements BaseSimplePojo {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;// 主键
    @Column(name = "postion")
    private String postion; // 广告位置：homePage、listPage、detailPage
    @Column(name = "ad_id")
    private Integer adId; // 广告id
    @JoinColumn(name = "ad_id", insertable = false, updatable = false)
    private Advertisement Advertisement;

    public AdRelation() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostion() {
        return postion;
    }

    public void setPostion(String postion) {
        this.postion = postion;
    }

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public Advertisement getAdvertisement() {
        return Advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        Advertisement = advertisement;
    }

}
