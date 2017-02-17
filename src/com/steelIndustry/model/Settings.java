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
@Table(name = "settings")
@NamedQuery(name = "Settings.findAll", query = "SELECT s FROM Settings s")
public class Settings implements BaseSimplePojo {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;// 主键
    @Column(name = "share_switch")
    private short shareSwitch; // 是否开启分享获取联系方式
    @Column(name = "is_check_store")
    private short isCheckStore; // 是否开启店铺审核
    @Column(name = "is_check_project")
    private short isCheckProject; // 是否开启工程审核
    @Column(name = "is_check_card")
    private short isCheckCard; // 是否开启名片审核
    @Column(name = "is_check_work")
    private short isCheckWork; // 是否开启用工需求审核
    @Column(name = "main_post_points")
    private int mainPostPoints; // 主题帖积分
    @Column(name = "replying_points")
    private int replyingPoints; // 回帖积分
    @Column(name = "home_page_ad_type")
    private String homePageAdType; // 首页广告位类型：alliance、loopImg、oneImg
    @Column(name = "list_page_ad_type")
    private String listPageAdType;// 列表页广告位类型：alliance、loopImg、oneImg
    @Column(name = "detail_page_ad_type")
    private String detailPageAdType;// 详情页广告位类型：alliance、loopImg、oneImg

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getShareWwitch() {
        return shareSwitch;
    }

    public void setShareWwitch(short shareSwitch) {
        this.shareSwitch = shareSwitch;
    }

    public short getIsCheckStore() {
        return isCheckStore;
    }

    public void setIsCheckStore(short isCheckStore) {
        this.isCheckStore = isCheckStore;
    }

    public short getIsCheckProject() {
        return isCheckProject;
    }

    public void setIsCheckProject(short isCheckProject) {
        this.isCheckProject = isCheckProject;
    }

    public short getIsCheckCard() {
        return isCheckCard;
    }

    public void setIsCheckCard(short isCheckCard) {
        this.isCheckCard = isCheckCard;
    }

    public short getIsCheckWork() {
        return isCheckWork;
    }

    public void setIsCheckWork(short isCheckWork) {
        this.isCheckWork = isCheckWork;
    }

    public int getMainPostPoints() {
        return mainPostPoints;
    }

    public void setMainPostPoints(int mainPostPoints) {
        this.mainPostPoints = mainPostPoints;
    }

    public int getReplyingPoints() {
        return replyingPoints;
    }

    public void setReplyingPoints(int replyingPoints) {
        this.replyingPoints = replyingPoints;
    }

    public String getHomePageAdType() {
        return homePageAdType;
    }

    public void setHomePageAdType(String homePageAdType) {
        this.homePageAdType = homePageAdType;
    }

    public String getListPageAdType() {
        return listPageAdType;
    }

    public void setListPageAdType(String listPageAdType) {
        this.listPageAdType = listPageAdType;
    }

    public String getDetailPageAdType() {
        return detailPageAdType;
    }

    public void setDetailPageAdType(String detailPageAdType) {
        this.detailPageAdType = detailPageAdType;
    }
}
