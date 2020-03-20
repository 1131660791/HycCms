package com.dome.web.sysrole.service;

import java.util.List;
import java.util.Map;

import com.dome.bean.TSRole;
import com.dome.bean.vo.TSRoleVo;

public interface TSRoleService {
	
    int deleteByPrimaryKey(String id);

    int insert(TSRole record);

    int insertSelective(TSRole record);

    TSRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TSRole record);

    int updateByPrimaryKey(TSRole record);
    
    /**
     * 查询用户角色
     * @param parameter
     * @return
     */
	List<TSRole> findUserRoleInfo(Map<String, Object> parameter);
	
	/**
	 * 查询 系统管理员 类型 角色
	 * @param parameter
	 * @return
	 */
	List<TSRoleVo> findRolesPage(Map<String, Object> parameter);
}
