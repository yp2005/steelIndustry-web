package com.steelIndustry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.steelIndustry.framework.base.BaseSimplePojo;

@Entity
@Table(name = "relation_table")
@NamedQuery(name = "RelationTable.findAll", query = "SELECT t FROM RelationTable t")
public class RelationTable implements BaseSimplePojo {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;// 主键
    @Column(name = "relation_master_table")
    private String relationMasterTable; // 主表：master_card、project、store、employment_demand
    @Column(name = "relation_slave_table")
    private String relationSlaveTable; // 从表：worker_type、device_type、area_data、img_xxx(图片没有表表示图片类型)
    @Column(name = "relation_master_id")
    private int relationMasterId; // 关联主表id
    @Column(name = "relation_slave_id")
    private int relationSlaveId; // 关联从表id
    @Column(name = "img_name")
    private String imgName; // 关联是图片时存放图片名称

    public RelationTable() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelationMasterTable() {
        return relationMasterTable;
    }

    public void setRelationMasterTable(String relationMasterTable) {
        this.relationMasterTable = relationMasterTable;
    }

    public String getRelationSlaveTable() {
        return relationSlaveTable;
    }

    public void setRelationSlaveTable(String relationSlaveTable) {
        this.relationSlaveTable = relationSlaveTable;
    }

    public int getRelationMasterId() {
        return relationMasterId;
    }

    public void setRelationMasterId(int relationMasterId) {
        this.relationMasterId = relationMasterId;
    }

    public int getRelationSlaveId() {
        return relationSlaveId;
    }

    public void setRelationSlaveId(int relationSlaveId) {
        this.relationSlaveId = relationSlaveId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}
