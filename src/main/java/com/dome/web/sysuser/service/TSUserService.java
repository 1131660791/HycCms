package com.dome.web.sysuser.service;

import java.util.List;
import java.util.Map;

import com.dome.bean.TSUser;
import com.dome.bean.vo.TSUserVo;

public interface TSUserService {
	
    int deleteByPrimaryKey(String id);

    int insert(TSUser record);

    int insertSelective(TSUser record);

    TSUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TSUser record);

    int updateByPrimaryKey(TSUser record);
    
    /**
     * 查询用户信息
     * @param parameter
     * @return
     */
    TSUserVo findSysUserInfo(Map<String, Object> parameter);
    
    /**
     * 查询医生信息
     */
    Map<String,Object> findDoctorInfo(Map<String, Object> parameter);
    
    /**
     * 查询所有用户
     * @return
     */
	List<TSUserVo> findUsersPage(Map<String, Object> parameter);
}
