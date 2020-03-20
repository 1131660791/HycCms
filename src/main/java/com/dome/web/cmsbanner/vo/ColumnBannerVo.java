package com.dome.web.cmsbanner.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 栏目BannerVo
 * @author admin
 *
 */
public class ColumnBannerVo implements Serializable{
	
	private String columnId; // 栏目Id
	
	private String columnRawId; // 栏目原Id
	
	private String columnPId; // 栏目父id
	
	private String columnName; // 栏目名称
	
	private String bannerTheme; // 主题
	
	private String hospitalId; // 医院id
	
	private String departname; // 机构名称
	
	private String platformId; // 平台Id
	
	private String platformName; // 平台名称
	
	private String updateTime; // 修改时间
	
	private String createUser; // 创建用户
	
	private int bannerCount;   // banner图片数量
	
	private String userName; // banner发布人
	
	private String createUserName; // 创建人姓名
	
	private String updateUserName; // 修改人姓名
	
	private List<ColumnBannerVo> columnBannerVos; // 子栏目

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	
	public String getColumnPId() {
		return columnPId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setColumnPId(String columnPId) {
		this.columnPId = columnPId;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getDepartname() {
		return departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
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

	public int getBannerCount() {
		return bannerCount;
	}

	public void setBannerCount(int bannerCount) {
		this.bannerCount = bannerCount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<ColumnBannerVo> getColumnBannerVos() {
		return columnBannerVos;
	}

	public void setColumnBannerVos(List<ColumnBannerVo> columnBannerVos) {
		this.columnBannerVos = columnBannerVos;
	}

	public String getBannerTheme() {
		return bannerTheme;
	}

	public void setBannerTheme(String bannerTheme) {
		this.bannerTheme = bannerTheme;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
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

	public String getColumnRawId() {
		return columnRawId;
	}

	public void setColumnRawId(String columnRawId) {
		this.columnRawId = columnRawId;
	}
	
	
	
} 
