package com.dome.web.sysrole.service;

import com.dome.bean.TSRoleUser;

public interface TSRoleUserService {
	
    int deleteByPrimaryKey(String id);

    int insert(TSRoleUser record);

    int insertSelective(TSRoleUser record);

    TSRoleUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TSRoleUser record);

    int updateByPrimaryKey(TSRoleUser record);
}
