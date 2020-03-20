package com.dome.web.sysplatformservice.vo;

import java.io.Serializable;
import java.util.List;

import com.dome.bean.TSPlatform;
import com.dome.bean.TSPlatformServiceCombo;

/**
 * 医生已有服务Vo
 * @author admin
 *
 */
public class CmsDoctorServiceVo implements Serializable{
	
	private String id;  // 平台服务id
	
	private String serviceName;  // 服务名称
	
	private String publicTime; // 发布时间
	
	private String expireTime; // 过期时间
	
	private int sort; // 权重
	
	private String fkColumnId; // 栏目id
	
	private String fkPlatformId; // 关联平台id
	
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

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getFkColumnId() {
		return fkColumnId;
	}

	public void setFkColumnId(String fkColumnId) {
		this.fkColumnId = fkColumnId;
	}

	public String getFkPlatformId() {
		return fkPlatformId;
	}

	public void setFkPlatformId(String fkPlatformId) {
		this.fkPlatformId = fkPlatformId;
	}

	public List<TSPlatformServiceCombo> getPlatformServiceCombos() {
		return platformServiceCombos;
	}

	public void setPlatformServiceCombos(List<TSPlatformServiceCombo> platformServiceCombos) {
		this.platformServiceCombos = platformServiceCombos;
	}
    
    
    
}
