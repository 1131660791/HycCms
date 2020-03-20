package com.dome.bean.vo;

import java.io.Serializable;
import java.util.List;

import com.dome.bean.TSDepart;

/**
 * 用户管理单位Vo
 * @author admin
 *
 */
public class CmsUserDepartVo implements Serializable {

	private String id; // 用户Id

	private String sex; // 性别

	private String username; // 用户名

	private String realname; // 真实姓名

	private String mobilephone; // 手机
	
	private String roleName; // 角色名称

	private List<TSDepart> departs; // 单位 - 一个管理员 能管理多个单位

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public List<TSDepart> getDeparts() {
		return departs;
	}

	public void setDeparts(List<TSDepart> departs) {
		this.departs = departs;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
