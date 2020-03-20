package com.dome.util.reverseback;

import java.util.Map;

/**
 * 返回数据类
 * @author admin
 */
public class ReverseBackData {
	
	private Object obj = null;	// 返回数据
	private String flag = "success";// 是否成功 success 成功，error: 失败
	private String msg = "操作成功";// 提示信息
	
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
