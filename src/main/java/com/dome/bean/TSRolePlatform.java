package com.dome.bean;

import java.io.Serializable;

/**
 * 角色平台关联表
 * @author admin
 *
 */
public class TSRolePlatform implements Serializable{
	
    private String id; // 主键id 

    private String fkRoleId; // 关联角色Id

    private String fkPlatformId; // 关联平台id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFkRoleId() {
        return fkRoleId;
    }

    public void setFkRoleId(String fkRoleId) {
        this.fkRoleId = fkRoleId == null ? null : fkRoleId.trim();
    }

    public String getFkPlatformId() {
        return fkPlatformId;
    }

    public void setFkPlatformId(String fkPlatformId) {
        this.fkPlatformId = fkPlatformId == null ? null : fkPlatformId.trim();
    }
}