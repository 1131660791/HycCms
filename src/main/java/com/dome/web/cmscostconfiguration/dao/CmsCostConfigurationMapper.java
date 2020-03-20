package com.dome.web.cmscostconfiguration.dao;

import java.util.List;
import java.util.Map;

import com.dome.bean.CmsCostConfiguration;

/**
 * 用户服务配置 数据访问层
 * @author admin
 *
 */
public interface CmsCostConfigurationMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(CmsCostConfiguration record);

    int insertSelective(CmsCostConfiguration record);

    CmsCostConfiguration selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsCostConfiguration record);

    int updateByPrimaryKey(CmsCostConfiguration record);
    
    /**
     *  修改医生状态 
     * @param parameter
     * @return
     */
	int updateDoctorState(Map<String, Object> parameter);
	
	/**
	 * 查询医生服务配置
	 * @param parameter
	 * @return
	 */
	List<CmsCostConfiguration> findDoctorService(Map<String, Object> parameter);

	/**
	 * 删除医生配置
	 * @param parameter
	 * @return
	 */
	int deleteDoctorConfig(Map<String, Object> parameter);
}