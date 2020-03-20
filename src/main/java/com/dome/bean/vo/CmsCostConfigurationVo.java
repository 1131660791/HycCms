package com.dome.bean.vo;

import java.io.Serializable;
import java.util.List;

import com.dome.bean.CmsOconfigDetail;

/**
 * 用户服务配置 Vo
 * @author admin
 *
 */
public class CmsCostConfigurationVo implements Serializable{
	
	private String id; // 主键ID

    private String hospitalId; // 医院ID

    private String hospitalName; // 医院名称

    private String configCreateTime; // 费用创建时间

    private String businessType; // 费用配置业务类型

    private String configUserId; // 配置人ID

    private String configOwnUserName; // 配置属于人姓名(根据业务需求可有可无)

    private String configOwnUserId; // 配置属于人ID(根据业务需求可有可无)

    private String createTime; // 创建时间

    private String updateTime; // 修改时间

    private Integer state; // 状态 : 0 启用 1:禁用

    private String fkPlatformId; // 关联平台id
    
    private List<CmsOconfigDetail> oconfigDetails; // 查询服务配置

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getConfigCreateTime() {
		return configCreateTime;
	}

	public void setConfigCreateTime(String configCreateTime) {
		this.configCreateTime = configCreateTime;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getConfigUserId() {
		return configUserId;
	}

	public void setConfigUserId(String configUserId) {
		this.configUserId = configUserId;
	}

	public String getConfigOwnUserName() {
		return configOwnUserName;
	}

	public void setConfigOwnUserName(String configOwnUserName) {
		this.configOwnUserName = configOwnUserName;
	}

	public String getConfigOwnUserId() {
		return configOwnUserId;
	}

	public void setConfigOwnUserId(String configOwnUserId) {
		this.configOwnUserId = configOwnUserId;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getFkPlatformId() {
		return fkPlatformId;
	}

	public void setFkPlatformId(String fkPlatformId) {
		this.fkPlatformId = fkPlatformId;
	}

	public List<CmsOconfigDetail> getOconfigDetails() {
		return oconfigDetails;
	}

	public void setOconfigDetails(List<CmsOconfigDetail> oconfigDetails) {
		this.oconfigDetails = oconfigDetails;
	}
    
}
