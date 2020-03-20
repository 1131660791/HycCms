package com.dome.web.sysroleplatform.dao;

import java.util.List;
import java.util.Map;

import com.dome.bean.TSRolePlatform;
import com.dome.web.sysroleplatform.vo.CmsRolePlatformVo;

public interface TSRolePlatformMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(TSRolePlatform record);

    int insertSelective(TSRolePlatform record);

    TSRolePlatform selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TSRolePlatform record);

    int updateByPrimaryKey(TSRolePlatform record);
    
    /**
     * 查询角色平台
     * @param parameter
     * @return
     */
	List<CmsRolePlatformVo> findRolePlatform(Map<String, Object> parameter);

	/**
	 * 删除角色的平台
	 * @param par
	 * @return
	 */
	int deleteRolePlatform(Map<String, Object> par);

	/**
	 * 角色管理的平台
	 * @param par
	 * @return
	 */
	List<TSRolePlatform> findRoleManagePlatform(Map<String, Object> par);
}