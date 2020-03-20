package com.dome.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 新闻表
 * @author admin
 *
 */
public class CmsNews implements Serializable{
	
    private String id; // 主键id

    private String fkColumnId; // 关联栏目Id

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

    private String newsContent;// 新闻内容
    
    private String createUser; // 添加人
    
    private String createUserName; // 编辑人姓名
    
    private String updateUser;  // 修改人
    
    private String updateUserName; // 修改人姓名
    
    private String createTime;// 创建时间

    private String updateTime;// 修改日期

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFkColumnId() {
		return fkColumnId;
	}

	public void setFkColumnId(String fkColumnId) {
		this.fkColumnId = fkColumnId;
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
        this.newsName = newsName == null ? null : newsName.trim();
    }

    public String getNewsWriter() {
        return newsWriter;
    }

    public void setNewsWriter(String newsWriter) {
        this.newsWriter = newsWriter == null ? null : newsWriter.trim();
    }

    public String getNewsKeyword() {
        return newsKeyword;
    }

    public void setNewsKeyword(String newsKeyword) {
        this.newsKeyword = newsKeyword == null ? null : newsKeyword.trim();
    }

    public String getNewsSource() {
        return newsSource;
    }

    public void setNewsSource(String newsSource) {
        this.newsSource = newsSource == null ? null : newsSource.trim();
    }

    public String getCoverLink() {
        return coverLink;
    }

    public void setCoverLink(String coverLink) {
        this.coverLink = coverLink == null ? null : coverLink.trim();
    }

    public String getNewsDigest() {
        return newsDigest;
    }

    public void setNewsDigest(String newsDigest) {
        this.newsDigest = newsDigest == null ? null : newsDigest.trim();
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
        this.fkPlatformId = fkPlatformId == null ? null : fkPlatformId.trim();
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName == null ? null : platformName.trim();
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId == null ? null : hospitalId.trim();
    }

    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname == null ? null : departname.trim();
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
        this.newsContent = newsContent == null ? null : newsContent.trim();
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