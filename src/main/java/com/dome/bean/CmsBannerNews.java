package com.dome.bean;

import java.io.Serializable;

/**
 * banner新闻关联表
 * 
 * @author admin
 * 
 */
public class CmsBannerNews implements Serializable {

	private String id; // 主键Id

	private String fkBannerId; // banner 内容Id

	private String fkNewsId; // 新闻id

	private String fkVideoId; // 视屏Id

	private String fkMaterialsId; // 学习资料Id

	private int type; // 关联类型 1:新闻 2:视频 3:学习资料

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

	public String getFkNewsId() {
		return fkNewsId;
	}

	public void setFkNewsId(String fkNewsId) {
		this.fkNewsId = fkNewsId == null ? null : fkNewsId.trim();
	}

	public String getFkVideoId() {
		return fkVideoId;
	}

	public void setFkVideoId(String fkVideoId) {
		this.fkVideoId = fkVideoId;
	}

	public String getFkMaterialsId() {
		return fkMaterialsId;
	}

	public void setFkMaterialsId(String fkMaterialsId) {
		this.fkMaterialsId = fkMaterialsId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}