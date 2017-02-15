package com.steelIndustry.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.steelIndustry.framework.base.BaseSimplePojo;

@Entity
@Table(name = "app_version")
@NamedQuery(name = "AppVersion.findAll", query = "SELECT a FROM AppVersion a")
public class AppVersion implements BaseSimplePojo {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;//主键
    @Column(name = "app_version")
    private String appVersion; // app版本号
    @Column(name = "description")
    private String description;// 版本描述
    @Column(name = "ios_url")
    private String iosUrl;// ios app url
    @Column(name = "android_url")
    private String androidUrl;// android app url
    @Column(name = "version_time")
    private Timestamp versionTime;// 版本更新时间

    public AppVersion() {
        
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIosUrl() {
        return iosUrl;
    }

    public void setIosUrl(String iosUrl) {
        this.iosUrl = iosUrl;
    }

    public String getAndroidUrl() {
        return androidUrl;
    }

    public void setAndroidUrl(String androidUrl) {
        this.androidUrl = androidUrl;
    }

    public Timestamp getVersionTime() {
        return versionTime;
    }

    public void setVersionTime(Timestamp versionTime) {
        this.versionTime = versionTime;
    }
}
