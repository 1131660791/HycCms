package com.dome.web.sysuser.dao;

import java.util.List;
import java.util.Map;

import com.dome.bean.TSUser;
import com.dome.bean.vo.TSUserVo;

public interface TSUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(TSUser record);

    int insertSelective(TSUser record);

    TSUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TSUser record);

    int updateByPrimaryKey(TSUser record);
    
    /**
     * 用户信息
     * @param parameter
     * @return
     */
	TSUserVo findSysUserInfo(Map<String, Object> parameter);
	
	/**
	 *  查询医生信息
	 * @param parameter
	 * @return
	 */
	Map<String,Object> findDoctorInfo(Map<String, Object> parameter);
	
	/**
	 * 查询所有用户
	 * @param parameter
	 * @return
	 */
	List<TSUserVo> findUsersPage(Map<String, Object> parameter);
}