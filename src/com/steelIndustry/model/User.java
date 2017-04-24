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
@Table(name = "user")
@NamedQuery(name = "User.findAll", query = "SELECT t FROM User t")
public class User implements BaseSimplePojo {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;// 主键
    @Column(name = "account")
    private String account; // 账号，现在没有，以后可能用到
    @Column(name = "mobile_number")
    private long mobileNumber; // 手机号码
    @Column(name = "password")
    private String password; // 密码，现在没有，以后可能用到
    @Column(name = "email")
    private String email; // 邮箱
    @Column(name = "user_name")
    private String userName; // 姓名
    @Column(name = "sex")
    private String sex; // 性别
    @Column(name = "birthday")
    private String birthday; // 生日
    @Column(name = "address")
    private String address; // 地址
    @Column(name = "company_name")
    private String companyName; // 公司名称
    @Column(name = "company_address")
    private String companyAddress; // 公司地址
    @Column(name = "avatar")
    private String avatar; // 头像
    @Column(name = "is_vip")
    private short isVip; // 是否是vip
    @Column(name = "is_admin")
    private short isAdmin; // 是否是管理员
    @Column(name = "is_shared")
    private short isShared; // 是否分享过app
    @Column(name = "real_name_authentication")
    private short realNameAuthentication; // 是否实名认证 0：为认证，1：通过认证，2：认证中，3：未通过认证
    @Column(name = "enterprise_certification")
    private short enterpriseCertification; // 是否企业认证 0：为认证，1：通过认证，2：认证中，3：未通过认证
    @Column(name = "state")
    private short state; // 用户状态，管理员封号用
    @Column(name = "points")
    private int points; // 论坛积分
    @Column(name = "gold")
    private int gold; // 金币
    @Column(name = "create_time")
    private Timestamp createTime; // 创建时间
    @Column(name = "update_time")
    private Timestamp updateTime; // 更新时间
    @Column(name = "latest_login_time")
    private Timestamp latestLoginTime; // 最后登录时间
    @Column(name = "instance_id")
    private String instanceId; // 用户登录设备id标识
    @Column(name = "access_token")
    private String accessToken; // access_token

    public User() {
        
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public short getIsVip() {
        return isVip;
    }

    public void setIsVip(short isVip) {
        this.isVip = isVip;
    }

    public short getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(short isAdmin) {
        this.isAdmin = isAdmin;
    }

    public short getIsShared() {
        return isShared;
    }

    public void setIsShared(short isShared) {
        this.isShared = isShared;
    }

    public short getRealNameAuthentication() {
        return realNameAuthentication;
    }

    public void setRealNameAuthentication(short realNameAuthentication) {
        this.realNameAuthentication = realNameAuthentication;
    }

    public short getEnterpriseCertification() {
        return enterpriseCertification;
    }

    public void setEnterpriseCertification(short enterpriseCertification) {
        this.enterpriseCertification = enterpriseCertification;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
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

    public Timestamp getLatestLoginTime() {
        return latestLoginTime;
    }

    public void setLatestLoginTime(Timestamp latestLoginTime) {
        this.latestLoginTime = latestLoginTime;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}