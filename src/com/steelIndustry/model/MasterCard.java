package com.steelIndustry.model;

import java.sql.Timestamp;
import java.util.List;

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
@Table(name = "master_card")
@NamedQuery(name = "MasterCard.findAll", query = "SELECT t FROM MasterCard t")
public class MasterCard implements BaseSimplePojo {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;// 主键
    @Column(name = "user_id")
    private int userId; // 用户id
    @Column(name = "contact")
    private String contact; // 联系人
    @Column(name = "mobile_number")
    private long mobileNumber; // 联系方式
    @Column(name = "province_id")
    private int provinceId; // 联系地址-省份id
    @Column(name = "province_name")
    private String provinceName; // 联系地址-省份名称
    @Column(name = "city_id")
    private int cityId; // 联系地址-市id
    @Column(name = "city_name")
    private String cityName; // 联系地址-市名称
    @Column(name = "county_id")
    private int countyId; // 联系地址-县id
    @Column(name = "county_name")
    private String countyName; // 联系地址-县名称
    @Column(name = "street")
    private String street; // 联系地址-详细地址
    @Column(name = "lng")
    private float lng; // 联系地址-经度
    @Column(name = "lat")
    private float lat; // 联系地址-纬度
    @Column(name = "working_years")
    private short workingYears; // 工龄
    @Column(name = "card_title")
    private String cardTitle; // 名片标题
    @Column(name = "description")
    private String description; // 自我介绍
    @Column(name = "browse_volume")
    private int browseVolume; // 浏览量
    @Column(name = "call_times")
    private int callTimes;//咨询次数
    @Column(name = "create_time")
    private Timestamp createTime; // 创建时间
    @Column(name = "update_time")
    private Timestamp updateTime; // 更新时间
    @Column(name = "is_working")
    private short isWorking; // 是否找到工作
    @Column(name = "state")
    private short state; // 状态：0草稿，1通过审核，2审核中，3审核不通过
    @Column(name = "sort")
    private Integer sort; // 排序字段
    @Transient
    private List<String> pictures;
    @Transient
    private List<WorkerType> workerTypes;
    @Transient
    private List<AreaData> serviceArea;
    @Transient
    private short realNameAuthentication; // 是否实名认证
    @Transient
    private short isCollected;//是否收藏
    @Transient
    private String imgServer;

    public String getImgServer() {
        return imgServer;
    }

    public void setImgServer(String imgServer) {
        this.imgServer = imgServer;
    }

    public short getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(short isCollected) {
        this.isCollected = isCollected;
    }

    public MasterCard() {

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

    public short getWorkingYears() {
        return workingYears;
    }

    public void setWorkingYears(short workingYears) {
        this.workingYears = workingYears;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
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

    public short getIsWorking() {
        return isWorking;
    }

    public void setIsWorking(short isWorking) {
        this.isWorking = isWorking;
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

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public List<WorkerType> getWorkerTypes() {
        return workerTypes;
    }

    public void setWorkerTypes(List<WorkerType> workerTypes) {
        this.workerTypes = workerTypes;
    }

    public List<AreaData> getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(List<AreaData> serviceArea) {
        this.serviceArea = serviceArea;
    }

    public short getRealNameAuthentication() {
        return realNameAuthentication;
    }

    public void setRealNameAuthentication(short realNameAuthentication) {
        this.realNameAuthentication = realNameAuthentication;
    }

    public int getCallTimes() {
        return callTimes;
    }

    public void setCallTimes(int callTimes) {
        this.callTimes = callTimes;
    }
}
