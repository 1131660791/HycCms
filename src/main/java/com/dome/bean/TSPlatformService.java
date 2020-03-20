package com.dome.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台服务表
 */
public class TSPlatformService implements Serializable {
	
    private String id;  // 平台服务id

    private String serviceName;  // 服务名称
 
    private String fkPlatformId; // 关联平台id
    
    private int state; // 状态  

    private String createUser;	// 创建人
    
    private String updateUser;  // 修改人
    
	private String createUserName; // 创建人姓名
	
	private String updateUserName; // 修改人姓名

    private String createTime;	// 创建时间

    private String updateTime;	// 修改时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName == null ? null : serviceName.trim();
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

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
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

}