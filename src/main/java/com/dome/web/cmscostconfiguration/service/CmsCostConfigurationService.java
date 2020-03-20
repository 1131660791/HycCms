package com.dome.web.cmscostconfiguration.service;

import java.util.Map;

import com.dome.bean.CmsCostConfiguration;

/**
 * 用户服务配置 业务层
 * @author admin
 *
 */
public interface CmsCostConfigurationService {
	
    int deleteByPrimaryKey(String id);

    int insert(CmsCostConfiguration record);

    int insertSelective(CmsCostConfiguration record);

    CmsCostConfiguration selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsCostConfiguration record);

    int updateByPrimaryKey(CmsCostConfiguration record);
    
    /**
     * 修改医生状态
     * @param parameter
     * @return
     */
	int updateDoctorState(Map<String, Object> parameter);
	
	/**
	 * 保存医生服务配置
	 * @param par
	 * @return
	 */
	int saveDoctorPlatformService(Map<String, Object> par);
}
