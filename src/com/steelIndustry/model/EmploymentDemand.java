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
@Table(name = "employment_demand")
@NamedQuery(name = "EmploymentDemand.findAll", query = "SELECT t FROM EmploymentDemand t")
public class EmploymentDemand implements BaseSimplePojo {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;// 主键
    @Column(name = "user_id")
    private int userId;// 发布用工需求的用户id
    @Column(name = "company_name")
    private String companyName;// 公司名称
    @Column(name = "contact")
    private String contact;// 联系人
    @Column(name = "mobile_number")
    private long mobileNumber;// 联系方式
    @Column(name = "demand_title")
    private String demandTitle;// 用工需求标题
    @Column(name = "province_id")
    private int provinceId;// 工作地点-省份id
    @Column(name = "province_name")
    private String provinceName;// 工作地点-省份名称
    @Column(name = "city_id")
    private int cityId;// 工作地点-市id
    @Column(name = "city_name")
    private String cityName;// 工作地点-市名称
    @Column(name = "county_id")
    private int countyId;// 工作地点-县id
    @Column(name = "county_name")
    private String countyName;// 工作地点-县名称
    @Column(name = "street")
    private String street;// 工作地点-详细地址
    @Column(name = "lng")
    private float lng;// 工作地点-经度
    @Column(name = "lat")
    private float lat;// 工作地点-纬度
    @Column(name = "description")
    private String description;// 用工需求介绍
    @Column(name = "browse_volume")
    private int browseVolume;// 浏览量
    @Column(name = "create_time")
    private Timestamp createTime;// 创建时间
    @Column(name = "update_time")
    private Timestamp updateTime;// 更新时间
    @Column(name = "due_time")
    private Timestamp dueTime;// 到期时间
    @Column(name = "state")
    private short state;// 状态：0草稿，1通过审核，2审核中
    @Column(name = "sort")
    private Integer sort;// 排序字段

    public EmploymentDemand() {

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDemandTitle() {
        return demandTitle;
    }

    public void setDemandTitle(String demandTitle) {
        this.demandTitle = demandTitle;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCountyId() {
        return countyId;
    }

    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBrowseVolume() {
        return browseVolume;
    }

    public void setBrowseVolume(int browseVolume) {
        this.browseVolume = browseVolume;
    }


    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Timestamp getDueTime() {
        return dueTime;
    }

    public void setDueTime(Timestamp dueTime) {
        this.dueTime = dueTime;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
