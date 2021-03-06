package com.dome.web.sysroleplatform.vo;

import java.io.Serializable;

/**
 * 角色 平台vo
 * @author admin
 */
public class CmsRolePlatformVo implements Serializable{
	
	private String id; // id

    private String platformName; // 平台名称

    private int state; // 状态 0:启用 1：禁用
    
    private String roleId; // 角色Id
    
    private String platformUrl; // 平台地址

    private String creationTime; // 创建时间

    private String updateTime; // 结束时间
    
    private String createUser;	// 创建人
    
    private String updateUser;  // 修改人
    
	private String createUserName; // 创建人姓名
	
	private String updateUserName; // 修改人姓名
	
	private int flag; // 0:该角色  未选择该平台 1:已选择

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getPlatformUrl() {
		return platformUrl;
	}

	public void setPlatformUrl(String platformUrl) {
		this.platformUrl = platformUrl;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
}
