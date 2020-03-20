package com.dome.web.sysplatform.service;

import java.util.List;
import java.util.Map;

import com.dome.bean.TSPlatform;

public interface TSPlatformService {

    int deleteByPrimaryKey(String id);

    int insert(TSPlatform record);

    int insertSelective(TSPlatform record);

    TSPlatform selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TSPlatform record);

    int updateByPrimaryKey(TSPlatform record);
    
    /**
     * 查询角色平台信息
     * @param parameter
     * @return
     */
	List<TSPlatform> findRolePlatforms(Map<String, Object> parameter);
	
	/**
	 * 查询平台列表 
	 * @param parameter
	 * @return
	 */
	List<TSPlatform> findPlatformsPage(Map<String, Object> parameter);
	
	/**
	 * 查询所有平台
	 * @return
	 */
	List<TSPlatform> findPlatformList();
}
