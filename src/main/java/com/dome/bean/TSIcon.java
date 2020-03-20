package com.dome.bean;

import java.io.Serializable;

/**
 * 菜单图标
 * @author admin
 */
public class TSIcon implements Serializable{
	
    private String id; // 图标Id

    private String extend; // 图片后缀

    private String iconclas; // 类型

    private String name; // 名字

    private Short type; // 类型 1系统图标/2菜单图标/3桌面图标
    
    private byte[] content; // 图片流内容

    private String path; // 路径
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend == null ? null : extend.trim();
    }

    public String getIconclas() {
        return iconclas;
    }

    public void setIconclas(String iconclas) {
        this.iconclas = iconclas == null ? null : iconclas.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
    
}