package com.dome.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户基础表
 * 
 * @author admin
 */
public class TSUser implements Serializable {

	private String id; // 用户Id

	private String email; // 邮箱

	private String mobilephone; // 手机

	private String officephone; // 办公电话

	private String signaturefile; // 签名文件
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
	/** 头像 */
	private String portrait;

	private String imsign;
	/** 开发权限标志 */
	private String devFlag;
	/** 用户类型 1:系统用户 \2接口用户 3 医院用户 4 机构用户 11：一般用户 6：本院专家 */
	private String userType;
	/** 人员类型 */
	private String personType;

	private String sex; // 性别

	private String empNo; // 工号

	private String citizenNo;// 身份证号

	private String fax;// 传真

	private String address;// 联系地址

	private String post;// 邮编

	private String memo; // 备注

	private String speciality; // 专业

	private String expertType; // 专家类型

	private String expertTitle; // 专家职称

	private String expertProfile; // 专家个人简介

	private String expertLevel; // 医生级别

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone == null ? null : mobilephone.trim();
	}

	public String getOfficephone() {
		return officephone;
	}

	public void setOfficephone(String officephone) {
		this.officephone = officephone == null ? null : officephone.trim();
	}

	public String getSignaturefile() {
		return signaturefile;
	}

	public void setSignaturefile(String signaturefile) {
		this.signaturefile = signaturefile == null ? null : signaturefile.trim();
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

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait == null ? null : portrait.trim();
	}

	public String getImsign() {
		return imsign;
	}

	public void setImsign(String imsign) {
		this.imsign = imsign == null ? null : imsign.trim();
	}

	public String getDevFlag() {
		return devFlag;
	}

	public void setDevFlag(String devFlag) {
		this.devFlag = devFlag == null ? null : devFlag.trim();
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType == null ? null : userType.trim();
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType == null ? null : personType.trim();
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex == null ? null : sex.trim();
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo == null ? null : empNo.trim();
	}

	public String getCitizenNo() {
		return citizenNo;
	}

	public void setCitizenNo(String citizenNo) {
		this.citizenNo = citizenNo == null ? null : citizenNo.trim();
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

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post == null ? null : post.trim();
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo == null ? null : memo.trim();
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality == null ? null : speciality.trim();
	}

	public String getExpertType() {
		return expertType;
	}

	public void setExpertType(String expertType) {
		this.expertType = expertType == null ? null : expertType.trim();
	}

	public String getExpertTitle() {
		return expertTitle;
	}

	public void setExpertTitle(String expertTitle) {
		this.expertTitle = expertTitle == null ? null : expertTitle.trim();
	}

	public String getExpertProfile() {
		return expertProfile;
	}

	public void setExpertProfile(String expertProfile) {
		this.expertProfile = expertProfile == null ? null : expertProfile.trim();
	}

	public String getExpertLevel() {
		return expertLevel;
	}

	public void setExpertLevel(String expertLevel) {
		this.expertLevel = expertLevel == null ? null : expertLevel.trim();
	}
}