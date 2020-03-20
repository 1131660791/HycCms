package com.dome.bean.vo;

import java.io.Serializable;

import com.dome.bean.TSPlatform;

/**
 * 平台服务Vo
 * @author admin
 *
 */
public class TSPlatformServiceVo implements Serializable{

	private String userId; // 用户id
	
	private String realname; // 用户真实名称
	
	private String departname; // 单位名称
	
    private String serviceName;  // 服务名称
    
    private String servicePublicName; // 服务发布人名称
 
    private TSPlatform platform; // 关联平台id

    private String createUser;	// 创建人

    private String createUserName; // 创建人姓名
	
	private String updateUserName; // 修改人姓名
	
    private int state; // 状态  0：启用
    
    private String columnName; // 医生推荐到的栏目名称
    
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getDepartname() {
		return departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServicePublicName() {
		return servicePublicName;
	}

	public void setServicePublicName(String servicePublicName) {
		this.servicePublicName = servicePublicName;
	}

	public TSPlatform getPlatform() {
		return platform;
	}

	public void setPlatform(TSPlatform platform) {
		this.platform = platform;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
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

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	
}
