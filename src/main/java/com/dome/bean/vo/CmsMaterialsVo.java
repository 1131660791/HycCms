package com.dome.bean.vo;

import java.io.Serializable;

/**
 * 学习资料Vo
 * 
 * @author admin
 *
 */
public class CmsMaterialsVo implements Serializable {

	private String id; // 主键id

	private String fkColumnId; // 关联栏目id

	private String materialsName; // 文件名称

	private String materialsLink; // 文件封面图

	private String materialsDescribe; // 文件描述

	private Integer quoteBannerFlag; // 是否被banner 关联 0 未关联 1 已关联

	private Integer chargeType; // 收费方式 0:免费 1:付费

	private Integer learnNumber; // 观看次数

	private Double materialsPrice; // 资料价格

	private String materialsAttName; // 资料附件名称

	private String materialsUrl; // 资料附件路径

	private Integer sort; // 权重

	private Integer state; // 状态: 0:发布 1:草稿

	private Integer materialsArrposid; // 资料推荐位置: 0:栏目 1:首页

	private String fkPlatformId; // 关联平台Id

	private String platformName; // 平台名称

	private String hospitalId; // 医院Id

	private String departname; // 所属机构名称

	private String sectionId; // 科室id

	private String sectionName; // 科室名称

	private String speechmaker; // 主讲人

	private String publicTime; // 发布时间

	private String expireTime; // 过期时间

	private String createTime; // 创建时间

	private String updateTime; // 修改日期

	private String createUser;// 创建人

	private String createUserName;// 创建人姓名

	private String updateUser;// 修改人

	private String updateUserName;// 修改人姓名

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

	public String getMaterialsName() {
		return materialsName;
	}

	public Integer getQuoteBannerFlag() {
		return quoteBannerFlag;
	}

	public void setQuoteBannerFlag(Integer quoteBannerFlag) {
		this.quoteBannerFlag = quoteBannerFlag;
	}

	public void setMaterialsName(String materialsName) {
		this.materialsName = materialsName == null ? null : materialsName.trim();
	}

	public String getMaterialsLink() {
		return materialsLink;
	}

	public void setMaterialsLink(String materialsLink) {
		this.materialsLink = materialsLink == null ? null : materialsLink.trim();
	}

	public String getMaterialsDescribe() {
		return materialsDescribe;
	}

	public void setMaterialsDescribe(String materialsDescribe) {
		this.materialsDescribe = materialsDescribe == null ? null : materialsDescribe.trim();
	}

	public Integer getChargeType() {
		return chargeType;
	}

	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}

	public Double getMaterialsPrice() {
		return materialsPrice;
	}

	public void setMaterialsPrice(Double materialsPrice) {
		this.materialsPrice = materialsPrice;
	}

	public String getMaterialsAttName() {
		return materialsAttName;
	}

	public void setMaterialsAttName(String materialsAttName) {
		this.materialsAttName = materialsAttName;
	}

	public String getMaterialsUrl() {
		return materialsUrl;
	}

	public void setMaterialsUrl(String materialsUrl) {
		this.materialsUrl = materialsUrl == null ? null : materialsUrl.trim();
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getMaterialsArrposid() {
		return materialsArrposid;
	}

	public void setMaterialsArrposid(Integer materialsArrposid) {
		this.materialsArrposid = materialsArrposid;
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
