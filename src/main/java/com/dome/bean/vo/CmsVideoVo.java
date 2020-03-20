package com.dome.bean.vo;

import java.io.Serializable;

/**
 * 视频vo
 * @author admin
 *
 */
public class CmsVideoVo implements Serializable{
	
	private String id; // 主键id
	
	private Integer quoteBannerFlag; // 是否被banner 关联 0 未关联 1 已关联
	
	private String videoTitile; // 视频标题
	
	private Integer sort; // 视屏权重
	
	private Integer videoArrposid; // 视频推荐位置 0:普通视频,1：首页视频,2:栏目视频
	
	private Integer state; // 1: 保存草稿 0:发布
	
	private String updateUser; // 修改用户

	private String updateUserName; // 修改用户名称

	private String updateTime;// 修改日期

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVideoTitile() {
		return videoTitile;
	}

	public void setVideoTitile(String videoTitile) {
		this.videoTitile = videoTitile;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getVideoArrposid() {
		return videoArrposid;
	}

	public void setVideoArrposid(Integer videoArrposid) {
		this.videoArrposid = videoArrposid;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public Integer getQuoteBannerFlag() {
		return quoteBannerFlag;
	}

	public void setQuoteBannerFlag(Integer quoteBannerFlag) {
		this.quoteBannerFlag = quoteBannerFlag;
	}
	
}
