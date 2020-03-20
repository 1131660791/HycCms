package com.dome.bean.vo;

import java.io.Serializable;
import java.util.List;

import com.dome.bean.TSPlatform;
import com.dome.bean.TSPlatformServiceCombo;

/**
 * 平台服务 Vo
 * 
 * @author admin
 */
public class PlatformServiceVo implements Serializable {

	private String id;  // 服务id
	
	private String serviceName;  // 服务名称
	
	private String createUser;	// 创建人
    
    private String updateUser;  // 修改人
    
	private String createUserName; // 创建人姓名
	
	private String updateUserName; // 修改人姓名

	private List<TSPlatformServiceCombo> platformServiceCombos; // 平台服务套餐

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
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

	public List<TSPlatformServiceCombo> getPlatformServiceCombos() {
		return platformServiceCombos;
	}

	public void setPlatformServiceCombos(List<TSPlatformServiceCombo> platformServiceCombos) {
		this.platformServiceCombos = platformServiceCombos;
	}
	
	
}
