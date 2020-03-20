package com.dome.bean.vo;

import java.io.Serializable;
import java.util.List;

import com.dome.bean.CmsBannerContent;
import com.dome.bean.CmsColumn;
import com.dome.bean.CmsNews;
import com.dome.bean.TSPlatform;

/**
 * bannerVo
 * @author admin
 *
 */
public class CmsBannerVo implements Serializable{

	private String id;  // banner主键Id
	
    private String bannerTheme; // 主题
    
    private CmsColumn column; // 关联栏目id

    private String hospitalId; // 医院id

    private String departname; // 所属机构名称

    private TSPlatform platform; // 平台id

    private Integer state; // banner状态,0:发布,1:禁用

    private String publicTime; // 发布时间

    private String publicUser; // 发布人

    private String expireTime; // 过期时间

    private String createUser; // 创建人

    private String updateUser; // 修改人

    private String createUserName; // 创建人姓名
	
	private String updateUserName; // 修改人姓名
	
    private String createTime; // 创建时间

    private String updateTime; // 修改时间
    
    private List<CmsBannerContentVo> bannerContents; // banner 内容
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBannerTheme() {
		return bannerTheme;
	}

	public void setBannerTheme(String bannerTheme) {
		this.bannerTheme = bannerTheme;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getDepartname() {
		return departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getPublicTime() {
		return publicTime;
	}

	public void setPublicTime(String publicTime) {
		this.publicTime = publicTime;
	}

	public String getPublicUser() {
		return publicUser;
	}

	public void setPublicUser(String publicUser) {
		this.publicUser = publicUser;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
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


	public List<CmsBannerContentVo> getBannerContents() {
		return bannerContents;
	}

	public void setBannerContents(List<CmsBannerContentVo> bannerContents) {
		this.bannerContents = bannerContents;
	}

	public CmsColumn getColumn() {
		return column;
	}

	public void setColumn(CmsColumn column) {
		this.column = column;
	}

	public TSPlatform getPlatform() {
		return platform;
	}

	public void setPlatform(TSPlatform platform) {
		this.platform = platform;
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
}
