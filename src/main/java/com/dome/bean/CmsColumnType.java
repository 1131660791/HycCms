package com.dome.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * cms栏目类型
 * 类型为：
 * @author admin
 */
public class CmsColumnType implements Serializable{
	
    private String id; // 类型id

    private String typeName; // 类型名称

    private Integer state; // 状态:0 启用，1:禁用

    private String creationTime; // 创建时间

    private String updateTime; // 修改时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

   
}