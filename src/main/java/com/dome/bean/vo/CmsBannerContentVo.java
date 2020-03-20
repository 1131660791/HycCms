package com.dome.bean.vo;

import java.io.Serializable;

/**
 * 新闻内容 Vo
 * @author admin
 *
 */
public class CmsBannerContentVo implements Serializable{
	
    private String id; // banner内容主键

    private String fkBannerId; // 关联bannerId

    private String bannerKeyword; // 关键字

    private String bannerName; // banner名称
 
    private String bannerUrl; //  banner图片路径

    private Integer sort; //  排序

    private Integer bannerLinkFlag; //  banner链接类型: 0:内联,1外联，2: 无连接

    private String bannerLink; // banner链接

    private String bannerTarget; // banner: html页面打开方式

    private Integer quoteFlag; // 是否被关联:0:否,1是

    private String createTime; // 创建时间

    private String updateTime; // 修改时间
    
    private String newsId; // 新闻id
    
    private String newsName;// 标题
    
    private String newsColumnId; // 新闻栏目id
    
    private String newscolumnName; // 栏目名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFkBannerId() {
        return fkBannerId;
    }

    public void setFkBannerId(String fkBannerId) {
        this.fkBannerId = fkBannerId == null ? null : fkBannerId.trim();
    }

    public String getBannerKeyword() {
        return bannerKeyword;
    }

    public void setBannerKeyword(String bannerKeyword) {
        this.bannerKeyword = bannerKeyword == null ? null : bannerKeyword.trim();
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName == null ? null : bannerName.trim();
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl == null ? null : bannerUrl.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getBannerLinkFlag() {
        return bannerLinkFlag;
    }

    public void setBannerLinkFlag(Integer bannerLinkFlag) {
        this.bannerLinkFlag = bannerLinkFlag;
    }

    public String getBannerLink() {
        return bannerLink;
    }

    public void setBannerLink(String bannerLink) {
        this.bannerLink = bannerLink == null ? null : bannerLink.trim();
    }

    public String getBannerTarget() {
        return bannerTarget;
    }

    public void setBannerTarget(String bannerTarget) {
        this.bannerTarget = bannerTarget == null ? null : bannerTarget.trim();
    }

    public Integer getQuoteFlag() {
        return quoteFlag;
    }

    public void setQuoteFlag(Integer quoteFlag) {
        this.quoteFlag = quoteFlag;
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

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getNewsName() {
		return newsName;
	}

	public void setNewsName(String newsName) {
		this.newsName = newsName;
	}

	public String getNewsColumnId() {
		return newsColumnId;
	}

	public void setNewsColumnId(String newsColumnId) {
		this.newsColumnId = newsColumnId;
	}

	public String getNewscolumnName() {
		return newscolumnName;
	}

	public void setNewscolumnName(String newscolumnName) {
		this.newscolumnName = newscolumnName;
	}

}
