package com.steelIndustry.bo;

import java.util.List;

public class Conditions {
    private Integer rowStartNumber;
    private Integer rowCount;
    private String keyword;
    private Integer provinceId;
    private Integer cityId;
    private Integer countyId;
    private List<Integer> typeIds;
    private Short sortType;
    private Float lng;
    private Float lat;
    private Short realNameAuthentication;
    private Short enterpriseCertification;

    public Integer getRowStartNumber() {
        return rowStartNumber;
    }

    public void setRowStartNumber(Integer rowStartNumber) {
        this.rowStartNumber = rowStartNumber;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public List<Integer> getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(List<Integer> typeIds) {
        this.typeIds = typeIds;
    }

    public Short getSortType() {
        return sortType;
    }

    public void setSortType(Short sortType) {
        this.sortType = sortType;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Short getRealNameAuthentication() {
        return realNameAuthentication;
    }

    public void setRealNameAuthentication(Short realNameAuthentication) {
        this.realNameAuthentication = realNameAuthentication;
    }

    public Short getEnterpriseCertification() {
        return enterpriseCertification;
    }

    public void setEnterpriseCertification(Short enterpriseCertification) {
        this.enterpriseCertification = enterpriseCertification;
    }

}
