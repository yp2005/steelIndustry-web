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
@Table(name = "real_name_authentication")
@NamedQuery(name = "RealNameAuthentication.findAll", query = "SELECT t FROM RealNameAuthentication t")
public class RealNameAuthentication implements BaseSimplePojo {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;// 主键
    @Column(name = "user_id")
    private int userId;// 用户id
    @Column(name = "real_name")
    private String realName; // 实名
    @Column(name = "card_id")
    private String cardId; // 身份证号码
    @Column(name = "card_picture_obverse")
    private String cardPictureObverse;// 身份证正面照片
    @Column(name = "card_picture_reverse")
    private String cardPictureReverse;// 身份证反面照片
    @Column(name = "hand_card_picture")
    private String handCardPicture;// 手持身份证照片
    @Column(name = "full_face_picture")
    private String fullFacePicture;// 正面照
    @Column(name = "state")
    private short state; // 是否通过认证  0审核中，1审核通过，2审核中，3审核不通过
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

    public RealNameAuthentication() {

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

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public String getCardPictureObverse() {
        return cardPictureObverse;
    }

    public void setCardPictureObverse(String cardPictureObverse) {
        this.cardPictureObverse = cardPictureObverse;
    }

    public String getCardPictureReverse() {
        return cardPictureReverse;
    }

    public void setCardPictureReverse(String cardPictureReverse) {
        this.cardPictureReverse = cardPictureReverse;
    }

    public String getHandCardPicture() {
        return handCardPicture;
    }

    public void setHandCardPicture(String handCardPicture) {
        this.handCardPicture = handCardPicture;
    }

    public String getFullFacePicture() {
        return fullFacePicture;
    }

    public void setFullFacePicture(String fullFacePicture) {
        this.fullFacePicture = fullFacePicture;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

}
