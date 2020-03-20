package com.dome.bean.vo;

import java.io.Serializable;

import com.dome.bean.CmsColumn;
import com.dome.bean.TSBaseUser;

/**
 * 新闻Vo
 * @author admin
 *
 */
public class CmsNewsVo implements Serializable{
	
    private String id; // 主键id
    
    private Integer quoteBannerFlag; // 是否被banner 关联 0 未关联 1 已关联

    private CmsColumn column; // 关联栏目Id

    private Integer state;// 新闻状态,0:发布,1:草稿,2:删除

    private String newsName;// 标题

    private String newsWriter;// 作者

    private String newsKeyword;// 关键词

    private String newsSource;// 来源

    private String coverLink;// 封面图

    private String newsDigest;// 摘要

    private Integer sort;// 权重

    private Integer newsArrposid;// 推荐位置 0:普通新闻，1：首页新闻,2:栏目新闻

    private String fkPlatformId;// 关联平台id

    private String platformName;// 平台名称

    private String hospitalId;// 医院Id

    private String departname;// 所属机构名称

    private String publicTime;// 发布时间

    private String expireTime;// 过期时间

    private String createTime;// 创建时间

    private String updateTime;// 修改日期

    private String newsContent;// 新闻内容
    
    private TSBaseUser baseUser; // 添加人
    
    private String updateUser;  // 修改人

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CmsColumn getColumn() {
		return column;
	}

	public void setColumn(CmsColumn column) {
		this.column = column;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getNewsName() {
		return newsName;
	}

	public void setNewsName(String newsName) {
		this.newsName = newsName;
	}

	public String getNewsWriter() {
		return newsWriter;
	}

	public void setNewsWriter(String newsWriter) {
		this.newsWriter = newsWriter;
	}

	public String getNewsKeyword() {
		return newsKeyword;
	}

	public void setNewsKeyword(String newsKeyword) {
		this.newsKeyword = newsKeyword;
	}

	public String getNewsSource() {
		return newsSource;
	}

	public void setNewsSource(String newsSource) {
		this.newsSource = newsSource;
	}

	public String getCoverLink() {
		return coverLink;
	}

	public void setCoverLink(String coverLink) {
		this.coverLink = coverLink;
	}

	public String getNewsDigest() {
		return newsDigest;
	}

	public void setNewsDigest(String newsDigest) {
		this.newsDigest = newsDigest;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getNewsArrposid() {
		return newsArrposid;
	}

	public void setNewsArrposid(Integer newsArrposid) {
		this.newsArrposid = newsArrposid;
	}

	public String getFkPlatformId() {
		return fkPlatformId;
	}

	public void setFkPlatformId(String fkPlatformId) {
		this.fkPlatformId = fkPlatformId;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
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

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public TSBaseUser getBaseUser() {
		return baseUser;
	}

	public void setBaseUser(TSBaseUser baseUser) {
		this.baseUser = baseUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Integer getQuoteBannerFlag() {
		return quoteBannerFlag;
	}

	public void setQuoteBannerFlag(Integer quoteBannerFlag) {
		this.quoteBannerFlag = quoteBannerFlag;
	}
	
}
