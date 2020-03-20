package com.dome.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 医生 服务 费用配置
 * @author admin
 *
 */
public class CmsOconfigDetail implements Serializable{
	
	private String id; // 主键ID

    private String configId; // 费用配置ID

    private BigDecimal packageMoney; // 套餐费用

    private Double packageTime; // 套餐时长

    private String packageTimeUnit; // 套餐时间单位

    private String createTime; // 创建时间

    private String updateTime; // 修改时间

    private String fkServicecomboId; // 关联服务套餐id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId == null ? null : configId.trim();
    }

    public BigDecimal getPackageMoney() {
        return packageMoney;
    }

    public void setPackageMoney(BigDecimal packageMoney) {
        this.packageMoney = packageMoney;
    }

    public Double getPackageTime() {
        return packageTime;
    }

    public void setPackageTime(Double packageTime) {
        this.packageTime = packageTime;
    }

    public String getPackageTimeUnit() {
        return packageTimeUnit;
    }

    public void setPackageTimeUnit(String packageTimeUnit) {
        this.packageTimeUnit = packageTimeUnit == null ? null : packageTimeUnit.trim();
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

	public String getFkServicecomboId() {
		return fkServicecomboId;
	}

	public void setFkServicecomboId(String fkServicecomboId) {
		this.fkServicecomboId = fkServicecomboId;
	}

}