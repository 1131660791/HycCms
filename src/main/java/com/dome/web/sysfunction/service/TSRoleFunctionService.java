package com.dome.web.sysfunction.service;

import com.dome.bean.TSRoleFunction;

public interface TSRoleFunctionService {
	
    int deleteByPrimaryKey(String id);

    int insert(TSRoleFunction record);

    int insertSelective(TSRoleFunction record);

    TSRoleFunction selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TSRoleFunction record);

    int updateByPrimaryKey(TSRoleFunction record);
}
