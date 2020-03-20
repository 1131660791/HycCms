package com.dome.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 默认栏目
 * 
 * @author admin
 *
 */
public class CmsDefaultColumn implements Serializable {

	private String id; // 栏目id

	private String pId; // 父级id

	private String columnName; // 栏目名称

	private String fkColumnType; // 所属栏目类型

	private Integer state; // 状态 0 启用 1禁用

	private String columnCode; // 栏目Code

	private String columnLink; // 栏目链接地址

	private String columnUrl; // 栏目图片路径

	private Integer sort; // 栏目排序

	private String fkPlatformId; // 所属平台id

	private String createUser; // 编辑人Id

	private String createUserName; // 编辑人姓名

	private String updateUser; // 修改人Id

	private String updateUserName; // 修改人姓名

	private String createTime; // 创建时间

	private String updateTime; // 修改时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId == null ? null : pId.trim();
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName == null ? null : columnName.trim();
	}

	public String getFkColumnType() {
		return fkColumnType;
	}

	public void setFkColumnType(String fkColumnType) {
		this.fkColumnType = fkColumnType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getColumnLink() {
		return columnLink;
	}

	public void setColumnLink(String columnLink) {
		this.columnLink = columnLink == null ? null : columnLink.trim();
	}

	public String getColumnUrl() {
		return columnUrl;
	}

	public void setColumnUrl(String columnUrl) {
		this.columnUrl = columnUrl == null ? null : columnUrl.trim();
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getFkPlatformId() {
		return fkPlatformId;
	}

	public void setFkPlatformId(String fkPlatformId) {
		this.fkPlatformId = fkPlatformId == null ? null : fkPlatformId.trim();
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser == null ? null : createUser.trim();
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName == null ? null : createUserName.trim();
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser == null ? null : updateUser.trim();
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName == null ? null : updateUserName.trim();
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

}