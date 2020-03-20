package com.dome.bean.vo;

import java.io.Serializable;
import java.util.List;

import com.dome.bean.CmsColumnType;
import com.dome.bean.TSPlatform;

/**
 * 栏目Vo
 * 
 * @author admin
 */
public class CmsColumnVo implements Serializable {

	private String id; // 主键id

	private String columnId; // 栏目id

	private String pId; // 父级id

	private String columnName; // 栏目名称

	private int columnProperty; // 栏目属性:0:非默认栏目,1:为默认栏目

	private String columnCode; // 栏目Code

	private CmsColumnType columnType; // 所属栏目类型

	private Integer state; // 状态 0 启用 1禁用

	private String columnLink; // 栏目链接地址

	private String columnUrl; // 栏目图片路径

	private String createUser; // 编辑人Id

	private String createUserName; // 编辑人姓名

	private String updateUser; // 修改人Id

	private String updateUserName; // 修改人姓名

	private TSPlatform tsPlatform; // 所属平台id

	private String hospitalId; // 医院id

	private String departname; // 所属机构名称

	private Integer sort; // 栏目排序

	private String createTime; // 创建时间

	private String updateTime; // 修改时间

	private List<CmsColumnVo> cmsColumnVos; // 子栏目

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
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
		this.columnLink = columnLink;
	}

	public String getColumnUrl() {
		return columnUrl;
	}

	public void setColumnUrl(String columnUrl) {
		this.columnUrl = columnUrl;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public TSPlatform getTsPlatform() {
		return tsPlatform;
	}

	public void setTsPlatform(TSPlatform tsPlatform) {
		this.tsPlatform = tsPlatform;
	}

	public List<CmsColumnVo> getCmsColumnVos() {
		return cmsColumnVos;
	}

	public void setCmsColumnVos(List<CmsColumnVo> cmsColumnVos) {
		this.cmsColumnVos = cmsColumnVos;
	}

	public CmsColumnType getColumnType() {
		return columnType;
	}

	public void setColumnType(CmsColumnType columnType) {
		this.columnType = columnType;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public int getColumnProperty() {
		return columnProperty;
	}

	public void setColumnProperty(int columnProperty) {
		this.columnProperty = columnProperty;
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

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

}
