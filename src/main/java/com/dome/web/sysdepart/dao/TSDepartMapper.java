package com.dome.web.sysdepart.dao;

import java.util.List;
import java.util.Map;

import com.dome.bean.TSDepart;

public interface TSDepartMapper {
    int deleteByPrimaryKey(String id);

    int insert(TSDepart record);

    int insertSelective(TSDepart record);

    TSDepart selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TSDepart record);

    int updateByPrimaryKeyWithBLOBs(TSDepart record);

    int updateByPrimaryKey(TSDepart record);
    
    /**
     * 查询用户管理单位
     * @param parameter
     * @return
     */
	List<TSDepart> findUserDeparts(Map<String, Object> parameter);

	/**
	 * 查询所有一级单位
	 * @param parameter
	 * @return
	 */
	List<TSDepart> findTopDepartsPage(Map<String, Object> parameter);
	
	/**
	 * 查询医院科室
	 * @param parameter
	 * @return
	 */
	List<TSDepart> findHospitalSection(Map<String, Object> parameter);
}