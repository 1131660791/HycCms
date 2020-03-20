package com.dome.bean;

import java.io.Serializable;

/**
 * 用户管理单位关联表
 *  - 确定用户能管理那些单位
 * @author admin
 *
 */
public class CmsUserDepart implements Serializable{
	
    private String id; // 主键Id

    private String fkUserId; // 关联用户id

    private String fkDepartId; // 关联单位id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(String fkUserId) {
        this.fkUserId = fkUserId == null ? null : fkUserId.trim();
    }

    public String getFkDepartId() {
        return fkDepartId;
    }

    public void setFkDepartId(String fkDepartId) {
        this.fkDepartId = fkDepartId == null ? null : fkDepartId.trim();
    }
}