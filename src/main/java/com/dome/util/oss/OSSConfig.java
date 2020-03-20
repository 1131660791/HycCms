package com.dome.util.oss;

import java.io.IOException;

import com.dome.util.properties.PropertiesUtil;

/**
 * OSS 配置
 * @author admin
 *
 */
public class OSSConfig {

	private String endpoint; // 连接区域地址
	private String accessKeyId; // 连接keyId
	private String accessKeySecret; // 连接秘钥
	private String bucketName; // 需要存储的bucketName
	private String picLocation; // 图片保存路径

	public OSSConfig() {
		PropertiesUtil propertiesUtil = new PropertiesUtil();
		try {
			this.endpoint = propertiesUtil.getProperties("oss.endpoint", "/oss.properties");
			this.bucketName = propertiesUtil.getProperties("oss.bucketName", "/oss.properties");
			this.picLocation = propertiesUtil.getProperties("oss.picLocation", "/oss.properties");
			this.accessKeyId = propertiesUtil.getProperties("oss.accessKeyId", "/oss.properties");
			this.accessKeySecret = propertiesUtil.getProperties("oss.accessKeySecret", "/oss.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getPicLocation() {
		return picLocation;
	}

	public void setPicLocation(String picLocation) {
		this.picLocation = picLocation;
	}

}
