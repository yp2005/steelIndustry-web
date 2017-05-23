package com.steelIndustry.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.steelIndustry.framework.base.BaseSimplePojo;

@Entity
@Table(name = "enterprise_certification")
@NamedQuery(name = "EnterpriseCertification.findAll", query = "SELECT t FROM EnterpriseCertification t")
public class EnterpriseCertification implements BaseSimplePojo {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;// 主键
    @Column(name = "user_id")
    private int userId;// 用户id
    @Column(name = "legal_person_name")
    private String legalPersonName; // 法人姓名
    @Column(name = "company_name")
    private String companyName; // 公司名称
    @Column(name = "license")
    private String license; // 营业执照照片
    @Column(name = "state")
    private short state; // 是否通过认证 0审核中，1审核通过，2审核中，3审核不通过
    @Column(name = "create_time")
    private Timestamp createTime; // 创建时间
    @Transient
    private String imgServer; // 图片服务器url

    public String getImgServer() {
        return imgServer;
    }

    public void setImgServer(String imgServer) {
        this.imgServer = imgServer;
    }

    public EnterpriseCertification() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

}
