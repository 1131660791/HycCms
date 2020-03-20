package com.dome.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 单位表
 * @author admin
 *
 */
public class TSDepart implements Serializable{
	
    private String id; // 主键Id

    private String departname; // 单位名称

    private String parentdepartid; // 父级单位Id

    private String orgCode; // 机构编码

    private String orgType; // //机构编码    1:公司 2:部门 3:岗位  4:医院 5:科室

    private String mobile; //手机号

    private String fax;//传真

    private String address;//地址

    private String departOrder;// 排序

    private String departnameEn;//英文名

    private String departnameAbbr;//缩写

    private String orgLog;//

    private String memo;//备注

    private String createName;//创建人名称

    private String createBy;//创建人账号

    private String createDate;//创建时间

    private String updateName;//更新人名称

    private String updateBy;//更新人账号

    private String updateDate;//更新时间

    private String sysCompanyCode;//数据所属公司

    private String sysOrgCode;//数据所属部门

    private String description; //描述

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname == null ? null : departname.trim();
    }

    public String getParentdepartid() {
        return parentdepartid;
    }

    public void setParentdepartid(String parentdepartid) {
        this.parentdepartid = parentdepartid == null ? null : parentdepartid.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType == null ? null : orgType.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getDepartOrder() {
        return departOrder;
    }

    public void setDepartOrder(String departOrder) {
        this.departOrder = departOrder == null ? null : departOrder.trim();
    }

    public String getDepartnameEn() {
        return departnameEn;
    }

    public void setDepartnameEn(String departnameEn) {
        this.departnameEn = departnameEn == null ? null : departnameEn.trim();
    }

    public String getDepartnameAbbr() {
        return departnameAbbr;
    }

    public void setDepartnameAbbr(String departnameAbbr) {
        this.departnameAbbr = departnameAbbr == null ? null : departnameAbbr.trim();
    }

    public String getOrgLog() {
        return orgLog;
    }

    public void setOrgLog(String orgLog) {
        this.orgLog = orgLog == null ? null : orgLog.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }
    
    public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName == null ? null : updateName.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public String getSysCompanyCode() {
        return sysCompanyCode;
    }

    public void setSysCompanyCode(String sysCompanyCode) {
        this.sysCompanyCode = sysCompanyCode == null ? null : sysCompanyCode.trim();
    }

    public String getSysOrgCode() {
        return sysOrgCode;
    }

    public void setSysOrgCode(String sysOrgCode) {
        this.sysOrgCode = sysOrgCode == null ? null : sysOrgCode.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}