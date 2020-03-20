package com.dome.bean;

/**
 * 角色模块中间表
 * @author admin
 * 
 */
public class TSRoleFunction {
	
    private String id;  // 主键Id

    private String operation;  // 页面控件权限编码

    private String functionid; // 模块Id

    private String roleid; 	   // 角色Id

    private String datarule;   // 数据权限规则ID

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public String getFunctionid() {
        return functionid;
    }

    public void setFunctionid(String functionid) {
        this.functionid = functionid == null ? null : functionid.trim();
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

    public String getDatarule() {
        return datarule;
    }

    public void setDatarule(String datarule) {
        this.datarule = datarule == null ? null : datarule.trim();
    }
}