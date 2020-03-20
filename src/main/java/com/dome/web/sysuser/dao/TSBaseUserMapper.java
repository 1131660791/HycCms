package com.dome.web.sysuser.dao;

import java.util.Map;

import com.dome.bean.TSBaseUser;

public interface TSBaseUserMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(TSBaseUser record);

    int insertSelective(TSBaseUser record);

    TSBaseUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TSBaseUser record);

    int updateByPrimaryKeyWithBLOBs(TSBaseUser record);

    int updateByPrimaryKey(TSBaseUser record);

	TSBaseUser findBaseUserInfo(Map<String, Object> parameter);
}