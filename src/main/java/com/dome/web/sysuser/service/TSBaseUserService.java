package com.dome.web.sysuser.service;

import java.util.Map;

import com.dome.bean.TSBaseUser;

public interface TSBaseUserService {
	
    int deleteByPrimaryKey(String id);

    int insert(TSBaseUser record);

    int insertSelective(TSBaseUser record);

    TSBaseUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TSBaseUser record);

    int updateByPrimaryKeyWithBLOBs(TSBaseUser record);

    int updateByPrimaryKey(TSBaseUser record);
    
    /**查询用户信息*/
	TSBaseUser findBaseUserInfo(Map<String, Object> parameter);
}
