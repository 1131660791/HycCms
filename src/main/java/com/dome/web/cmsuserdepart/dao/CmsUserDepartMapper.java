package com.dome.web.cmsuserdepart.dao;

import java.util.List;
import java.util.Map;

import com.dome.bean.CmsUserDepart;
import com.dome.bean.TSDepart;
import com.dome.bean.vo.CmsUserDepartVo;

public interface CmsUserDepartMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(CmsUserDepart record);

    int insertSelective(CmsUserDepart record);

    CmsUserDepart selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsUserDepart record);

    int updateByPrimaryKey(CmsUserDepart record);
    
    /**
     * 查询用户 基础信息 和 用户能管理的 单位 - 管理员 配置用户 信息时使用
     * @param parameter
     * @return
     */
	List<CmsUserDepartVo> findUserManageDeparts(Map<String, Object> parameter);

	/**
	 * 删除用户管理的单位
	 * @param parameter
	 * 	userId - 用户Id 
	 * 	departId - 单位Id
	 */
	int deleteUserDepart(Map<String, Object> parameter);

	/**
	 * 删除用户所有管理的单位
	 * @param parameter
	 * 	userId - 用户Id 
	 */
	int deleteUserAllDepart(Map<String, Object> par);
   
}