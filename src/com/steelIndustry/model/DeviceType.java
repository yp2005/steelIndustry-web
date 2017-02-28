package com.steelIndustry.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.steelIndustry.framework.base.BaseSimplePojo;

@Entity
@Table(name = "device_type")
@NamedQuery(name = "DeviceType.findAll", query = "SELECT t FROM DeviceType t")
public class DeviceType implements BaseSimplePojo {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;// 主键
    @Column(name = "type_name")
    private String typeName; // 工种名称
    @Column(name = "parent_id")
    private Integer parentId; // 父类型id
    @ManyToOne
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    @JsonIgnore
    private DeviceType parentType;
    @OneToMany(mappedBy = "parentType", fetch = FetchType.EAGER)
    private List<DeviceType> children;
    @Column(name = "description")
    private String description; // 描述

    public DeviceType() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public DeviceType getParentType() {
        return parentType;
    }

    public void setParentType(DeviceType parentType) {
        this.parentType = parentType;
    }

    public List<DeviceType> getChildren() {
        return children;
    }

    public void setChildren(List<DeviceType> children) {
        this.children = children;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
