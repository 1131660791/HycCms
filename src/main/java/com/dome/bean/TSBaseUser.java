package com.dome.bean;

import java.io.Serializable;

/**
 * 用户扩展表
 * @author admin
 */
public class TSBaseUser  implements Serializable{
	
    private String id; // 用户Id 

    private Short activitisync;//是否同步工作流引擎

    private String browser; // 用户使用浏览器类型

    private String password; //用户密码

    private String realname; // 真实姓名

    private Short status; // 状态1：在线【空闲】 2：忙碌  0：禁用  3:离线

    private String userkey; // 用户验证唯一标示

    private String username; // 用户名

    private String departid; // 组织机构编码(多个组织机构编码用逗号分隔，非必填)

    private String userNameEn; //英文名

    private Short deleteFlag; // 状态: 0:不删除  1：删除

    private String expertGoodAt; // 擅长

    private String hospitalId; //医院ID

    private String wyAccid; //网易云视频唯一标识ID

    private String wyToken; // 网易账号令牌

    private String roomId; //科室ID

    private String doctorPnumber; //医生执业编号 doctor_pnumber

    private String nickName; // 用户昵称

    private byte[] signature; // 签名文件

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Short getActivitisync() {
        return activitisync;
    }

    public void setActivitisync(Short activitisync) {
        this.activitisync = activitisync;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser == null ? null : browser.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getUserkey() {
        return userkey;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey == null ? null : userkey.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid == null ? null : departid.trim();
    }

    public String getUserNameEn() {
        return userNameEn;
    }

    public void setUserNameEn(String userNameEn) {
        this.userNameEn = userNameEn == null ? null : userNameEn.trim();
    }

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getExpertGoodAt() {
        return expertGoodAt;
    }

    public void setExpertGoodAt(String expertGoodAt) {
        this.expertGoodAt = expertGoodAt == null ? null : expertGoodAt.trim();
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId == null ? null : hospitalId.trim();
    }

    public String getWyAccid() {
        return wyAccid;
    }

    public void setWyAccid(String wyAccid) {
        this.wyAccid = wyAccid == null ? null : wyAccid.trim();
    }

    public String getWyToken() {
        return wyToken;
    }

    public void setWyToken(String wyToken) {
        this.wyToken = wyToken == null ? null : wyToken.trim();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId == null ? null : roomId.trim();
    }

    public String getDoctorPnumber() {
        return doctorPnumber;
    }

    public void setDoctorPnumber(String doctorPnumber) {
        this.doctorPnumber = doctorPnumber == null ? null : doctorPnumber.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }
}