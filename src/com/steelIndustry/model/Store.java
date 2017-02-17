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
@Table(name = "store")
@NamedQuery(name = "Store.findAll", query = "SELECT t FROM Store t")
public class Store implements BaseSimplePojo {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;// 主键
    @Column(name = "user_id")
    private int userId;// 发布店铺信息的用户id
    @Column(name = "store_name")
    private String storeName;// 店铺名称
    @Column(name = "contact")
    private String contact;// 联系人
    @Column(name = "mobile_number")
    private int mobileNumber;// 联系方式
    @Column(name = "province_id")
    private int provinceId;// 商家地址-省份id
    @Column(name = "province_name")
    private String provinceName;// 商家地址-省份名称
    @Column(name = "city_id")
    private int cityId;// 商家地址-市id
    @Column(name = "city_name")
    private String cityName;// 商家地址-市名称
    @Column(name = "county_id")
    private int countyId;// 商家地址-县id
    @Column(name = "county_name")
    private String countyName;// 商家地址-县名称
    @Column(name = "street")
    private String street;// 商家地址-详细地址
    @Column(name = "lng")
    private float lng;// 商家地址-经度
    @Column(name = "lat")
    private float lat;// 商家地址-纬度
    @Column(name = "description")
    private String description;// 店铺信息介绍
    @Column(name = "browse_volume")
    private int browseVolume;// 浏览量
    @Column(name = "create_time")
    private Timestamp createTime;// 创建时间
    @Column(name = "update_time")
    private Timestamp updateTime;// 更新时间
    @Column(name = "state")
    private short state;// 状态：0草稿，1通过审核，2审核中
    @Column(name = "sort")
    private int sort;// 排序字段

    public Store() {

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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(int mobileNumber) {
        this.mobileNumber = mobileNumber;
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

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
