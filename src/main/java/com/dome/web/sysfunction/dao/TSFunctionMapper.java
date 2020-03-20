package com.dome.web.sysfunction.dao;

import java.util.List;
import java.util.Map;

import com.dome.bean.TSFunction;
import com.dome.bean.vo.TSFunctionVo;

public interface TSFunctionMapper {
	
    int deleteByPrimaryKey(String id);
    
    int insert(TSFunction record);
    
    int insertSelective(TSFunction record);
    
    TSFunction selectByPrimaryKey(String id);
    
    int updateByPrimaryKeySelective(TSFunction record);
    
    int updateByPrimaryKey(TSFunction record);
    
    /**
     * 根据角色Id查询所属模块
     * @param parameter
     * @return
     */
	List<TSFunctionVo> findRoleFunction(Map<String, Object> parameter);
}