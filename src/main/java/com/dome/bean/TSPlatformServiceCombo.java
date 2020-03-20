package com.dome.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台服务 套餐
 * @author admin
 *
 */
public class TSPlatformServiceCombo implements Serializable{
	
    private String id; // 主键id

    private String comboName; // 套餐名称

    private Integer packageMoney; // 套餐 费用

    private Integer packageTime; // 套餐时间

    private String packageTimeUnit; // 套餐单位

    private Integer state; // 状态 0:启用 ,1:禁用

    private String fkPlatformserviceId; // 关联平台服务id

    private String createTime; // 创建时间

    private String updateTime; // 修改时间

    private String createUser; // 创建人

    private String createUserName; // 创建人名称

    private String updateUser; // 修改人

    private String updateUserName; // 修改人 名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getComboName() {
        return comboName;
    }

    public void setComboName(String comboName) {
        this.comboName = comboName == null ? null : comboName.trim();
    }

    public Integer getPackageMoney() {
        return packageMoney;
    }

    public void setPackageMoney(Integer packageMoney) {
        this.packageMoney = packageMoney;
    }

    public Integer getPackageTime() {
        return packageTime;
    }

    public void setPackageTime(Integer packageTime) {
        this.packageTime = packageTime;
    }

    public String getPackageTimeUnit() {
        return packageTimeUnit;
    }

    public void setPackageTimeUnit(String packageTimeUnit) {
        this.packageTimeUnit = packageTimeUnit == null ? null : packageTimeUnit.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getFkPlatformserviceId() {
        return fkPlatformserviceId;
    }

    public void setFkPlatformserviceId(String fkPlatformserviceId) {
        this.fkPlatformserviceId = fkPlatformserviceId == null ? null : fkPlatformserviceId.trim();
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

	public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName == null ? null : updateUserName.trim();
    }
}