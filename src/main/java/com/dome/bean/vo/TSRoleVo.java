package com.dome.bean.vo;

import java.io.Serializable;
import java.util.List;

import com.dome.bean.TSPlatform;

/**
 * 角色Vo
 * 
 * @author admin
 *
 */
public class TSRoleVo implements Serializable {

	private String id; // 角色Id

	private String rolecode; // 角色Code

	private String rolename; // 角色名称

	private String departAgId; // 组织机构ID

	private String roleType; // 角色类型1部门角色/0系统角色/2医院角色

	/** 20180524新增字段 角色所属机构 */
	private String belongOrgId;

	/** 20180524新增字段 角色所属机构名称 */
	private String departname;

	/** 修改人名称 */
	private String updateName;

	/** 修改时间 */
	private String updateDate;

	/** 修改人 */
	private String updateBy;

	/** 创建人名称 */
	private String createName;

	/** 创建时间 */
	private String createDate;

	/** 创建人ID */
	private String createBy;
	
	// 平台名称
	private String platformName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRolecode() {
		return rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getDepartAgId() {
		return departAgId;
	}

	public void setDepartAgId(String departAgId) {
		this.departAgId = departAgId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getBelongOrgId() {
		return belongOrgId;
	}

	public void setBelongOrgId(String belongOrgId) {
		this.belongOrgId = belongOrgId;
	}

	public String getDepartname() {
		return departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	
}