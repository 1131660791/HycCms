package com.dome.web.sysfunction.service;

import java.util.List;
import java.util.Map;

import com.dome.bean.TSFunction;
import com.dome.bean.vo.TSFunctionVo;

public interface TSFunctionService {
	
    int deleteByPrimaryKey(String id);

    int insert(TSFunction record);

    int insertSelective(TSFunction record);

    TSFunction selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TSFunction record);

    int updateByPrimaryKey(TSFunction record);

	List<TSFunctionVo> findRoleFunction(Map<String, Object> parameter);
	
	/**
	 * 组合菜单树目录
	 * @param functions
	 * @return
	 */
	List<TSFunctionVo> loadFunctionTree(List<TSFunctionVo> functions);
}
