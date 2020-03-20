package com.dome.web.sysroleplatform.service;

import java.util.List;
import java.util.Map;

import com.dome.bean.TSRolePlatform;
import com.dome.web.sysroleplatform.vo.CmsRolePlatformVo;

public interface TSRolePlatformService {
	
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
	 * 保存角色 关联 平台
	 * @param rolePlatformJson
	 * {
			"roleId": "角色Id",
			"platformIds": [{
				"platformId": "平台id"
			}, {
				"platformId": "平台id"
			}]
		}
	 * @param response
	 */
	int saveRolePlatform(Map<String, Object> parameter);
	
	/**
	 * 查询角色管理的平台
	 * @param par
	 * @return
	 */
	List<TSRolePlatform> findRoleManagePlatform(Map<String, Object> par);
}
