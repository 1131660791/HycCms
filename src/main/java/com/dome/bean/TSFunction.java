package com.dome.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 模块表
 * @author admin
 *
 */
public class TSFunction implements Serializable{
	
    private String id;
    
    private Short functioniframe;//菜单地址打开方式
    
    private Short functionlevel; //菜单等级
    
    private String functionname; //菜单名称

    private String functionorder; //菜单排序
    
    private String functionurl; //菜单地址
    
    private String iconid; // 菜单图标 
   
   /* private String hospitalId;// 医院ID
    
    private String departname; // 菜单所属 机构Id
    
    private String platformId; // 模块所属 平台id
    
    private String platformName; // 模块所属平台 名称
*/    
    private String deskIconid;

    private Short functiontype; //菜单类型

    private String functionIconStyle; //菜单图标样式
    /**创建人ID*/
    private String createBy;
    /**创建人名称*/
    private String createName;
    /**修改人*/
    private String updateBy;
    /**修改时间*/
    private String updateDate;
    /**创建时间*/
    private String createDate;
    /**修改人名称*/
    private String updateName;
    
    private String parentfunctionid; // 父级菜单Id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Short getFunctioniframe() {
        return functioniframe;
    }

    public void setFunctioniframe(Short functioniframe) {
        this.functioniframe = functioniframe;
    }

    public Short getFunctionlevel() {
        return functionlevel;
    }

    public void setFunctionlevel(Short functionlevel) {
        this.functionlevel = functionlevel;
    }

    public String getFunctionname() {
        return functionname;
    }

    public void setFunctionname(String functionname) {
        this.functionname = functionname == null ? null : functionname.trim();
    }

    public String getFunctionorder() {
        return functionorder;
    }

    public void setFunctionorder(String functionorder) {
        this.functionorder = functionorder == null ? null : functionorder.trim();
    }

    public String getFunctionurl() {
        return functionurl;
    }

    public void setFunctionurl(String functionurl) {
        this.functionurl = functionurl == null ? null : functionurl.trim();
    }

    public String getDeskIconid() {
        return deskIconid;
    }

    public void setDeskIconid(String deskIconid) {
        this.deskIconid = deskIconid == null ? null : deskIconid.trim();
    }

    public Short getFunctiontype() {
        return functiontype;
    }

    public void setFunctiontype(Short functiontype) {
        this.functiontype = functiontype;
    }

    public String getFunctionIconStyle() {
        return functionIconStyle;
    }

    public void setFunctionIconStyle(String functionIconStyle) {
        this.functionIconStyle = functionIconStyle == null ? null : functionIconStyle.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }


    public String getIconid() {
		return iconid;
	}

	public void setIconid(String iconid) {
		this.iconid = iconid;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getParentfunctionid() {
		return parentfunctionid;
	}

	public void setParentfunctionid(String parentfunctionid) {
		this.parentfunctionid = parentfunctionid;
	}

	public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName == null ? null : updateName.trim();
    }
	
}