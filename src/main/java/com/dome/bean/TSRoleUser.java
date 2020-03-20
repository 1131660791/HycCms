package com.dome.bean;

/**
 * 用户角色中间表
 * @author admin
 *
 */
public class TSRoleUser {
	
    private String id; // 主键Id

    private String roleid; // 角色Id

    private String userid; // 用户Id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }
}