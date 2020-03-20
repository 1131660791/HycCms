package com.dome.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户服务配置
 * @author admin
 *
 */
public class CmsCostConfiguration implements Serializable{
	
    private String id; // 主键ID

    private String hospitalId; // 医院ID

    private String hospitalName; // 医院名称

    private String configCreateTime; // 费用创建时间

    private String businessType; // 费用配置业务类型
    
    private String configUserId; // 配置人ID
    
    private String configOwnUserName; // 配置属于人姓名(根据业务需求可有可无)
    
    private String configOwnUserId; // 配置属于人ID(根据业务需求可有可无)
    
    private int sort; // 权重
    
    private String createTime; // 创建时间
    
    private String updateTime; // 修改时间
    
    private String publicTime; // 发布时间
    
    private String expireTime; // 修改时间

    private String fkColumnId; // 关联栏目id
    
    private Integer state; // 状态 : 0 启用 1:禁用

    private String fkPlatformId; // 关联平台id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId == null ? null : hospitalId.trim();
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName == null ? null : hospitalName.trim();
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getConfigUserId() {
        return configUserId;
    }

    public void setConfigUserId(String configUserId) {
        this.configUserId = configUserId == null ? null : configUserId.trim();
    }

    public String getConfigOwnUserName() {
        return configOwnUserName;
    }

    public void setConfigOwnUserName(String configOwnUserName) {
        this.configOwnUserName = configOwnUserName == null ? null : configOwnUserName.trim();
    }

    public String getConfigOwnUserId() {
        return configOwnUserId;
    }

    public void setConfigOwnUserId(String configOwnUserId) {
        this.configOwnUserId = configOwnUserId == null ? null : configOwnUserId.trim();
    }

    public String getConfigCreateTime() {
		return configCreateTime;
	}

	public void setConfigCreateTime(String configCreateTime) {
		this.configCreateTime = configCreateTime;
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
        this.fkPlatformId = fkPlatformId == null ? null : fkPlatformId.trim();
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

	public String getFkColumnId() {
		return fkColumnId;
	}

	public void setFkColumnId(String fkColumnId) {
		this.fkColumnId = fkColumnId;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	
}