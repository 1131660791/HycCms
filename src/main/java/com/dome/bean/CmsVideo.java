package com.dome.bean;

import java.io.Serializable;

/**
 * 视频表
 * 
 * @author admin
 *
 */
public class CmsVideo implements Serializable {

	private String id; // 主键Id

	private String fkColumnId; // 关联栏目id

	private String videoTitile; // 视频标题

	private String videoName; // 视频文件名称

	private String videoLink; // 视频封面图

	private String videoDescribe; // 视频描述

	private Integer chargeType; // 收费方式 0:免费 1:付费

	private Integer learnNumber; // 观看次数

	private Double videoPrice; // 视频价格

	private String videoUrl; // 视频地址

	private Integer sort; // 视屏权重

	private Integer state; // 1: 保存草稿 0:发布

	private Integer videoArrposid; // 视频推荐位置 0:普通视频,1：首页视频,2:栏目视频

	private String fkPlatformId; // 关联平台id

	private String platformName; // 平台名称

	private String hospitalId; // 医院id

	private String departname; // 医院名称

	private String sectionId; // 科室id

	private String sectionName; // 科室名称

	private String speechmaker; // 主讲人

	private String publicTime; // 发布时间

	private String expireTime; // 过期时间

	private String createTime; // 创建时间

	private String updateTime; // 修改时间

	private String createUser; // 创建用户

	private String createUserName; // 创建用户名称

	private String updateUser; // 修改用户

	private String updateUserName; // 修改用户名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getFkColumnId() {
		return fkColumnId;
	}

	public void setFkColumnId(String fkColumnId) {
		this.fkColumnId = fkColumnId == null ? null : fkColumnId.trim();
	}

	public String getVideoTitile() {
		return videoTitile;
	}

	public void setVideoTitile(String videoTitile) {
		this.videoTitile = videoTitile == null ? null : videoTitile.trim();
	}

	public String getVideoLink() {
		return videoLink;
	}

	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink == null ? null : videoLink.trim();
	}

	public Integer getChargeType() {
		return chargeType;
	}

	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}

	public Double getVideoPrice() {
		return videoPrice;
	}

	public void setVideoPrice(Double videoPrice) {
		this.videoPrice = videoPrice;
	}

	public String getVideoDescribe() {
		return videoDescribe;
	}

	public void setVideoDescribe(String videoDescribe) {
		this.videoDescribe = videoDescribe == null ? null : videoDescribe.trim();
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl == null ? null : videoUrl.trim();
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getVideoArrposid() {
		return videoArrposid;
	}

	public void setVideoArrposid(Integer videoArrposid) {
		this.videoArrposid = videoArrposid;
	}

	public String getFkPlatformId() {
		return fkPlatformId;
	}

	public void setFkPlatformId(String fkPlatformId) {
		this.fkPlatformId = fkPlatformId == null ? null : fkPlatformId.trim();
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName == null ? null : platformName.trim();
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId == null ? null : hospitalId.trim();
	}

	public String getDepartname() {
		return departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname == null ? null : departname.trim();
	}

	public String getPublicTime() {
		return publicTime;
	}

	public void setPublicTime(String publicTime) {
		this.publicTime = publicTime;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName == null ? null : updateUserName.trim();
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getSpeechmaker() {
		return speechmaker;
	}

	public void setSpeechmaker(String speechmaker) {
		this.speechmaker = speechmaker;
	}

	public Integer getLearnNumber() {
		return learnNumber;
	}

	public void setLearnNumber(Integer learnNumber) {
		this.learnNumber = learnNumber;
	}

}