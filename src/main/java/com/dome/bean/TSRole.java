package com.dome.bean;

import java.util.Date;

/**
 * 角色表
 * @author admin
 *
 */
public class TSRole {
	
    private String id; // 角色Id

    private String rolecode; // 角色Code

    private String rolename; // 角色名称

    private String departAgId; // 组织机构ID

    private String roleType; //角色类型1部门角色/0系统角色/2医院角色
    
    /** 20180524新增字段 角色所属机构*/
    private String belongOrgId;
    
    /** 20180524新增字段 角色所属机构名称*/
    private String departname;
    
	/**修改人名称*/
    private String updateName;
    
    /**修改时间*/
    private String updateDate;
    
    /**修改人*/
    private String updateBy;
    
    /**创建人名称*/
    private String createName;
    
    /**创建时间*/
    private String createDate;
    
    /**创建人ID*/
    private String createBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRolecode() {
        return rolecode;
    }

    public void setRolecode(String rolecode) {
        this.rolecode = rolecode == null ? null : rolecode.trim();
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    public String getDepartAgId() {
        return departAgId;
    }

    public void setDepartAgId(String departAgId) {
        this.departAgId = departAgId == null ? null : departAgId.trim();
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType == null ? null : roleType.trim();
    }

    public String getBelongOrgId() {
        return belongOrgId;
    }

    public void setBelongOrgId(String belongOrgId) {
        this.belongOrgId = belongOrgId == null ? null : belongOrgId.trim();
    }

    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname == null ? null : departname.trim();
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName == null ? null : updateName.trim();
    }

    public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
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
}