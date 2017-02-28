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
    private short sortType;
    private float lng;
    private float lat;

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

    public void setSortType(short sortType) {
        this.sortType = sortType;
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

}
