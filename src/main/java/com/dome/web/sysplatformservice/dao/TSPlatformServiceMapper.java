package com.dome.web.sysplatformservice.dao;

import java.util.List;
import java.util.Map;

import com.dome.bean.TSPlatformService;
import com.dome.bean.vo.PlatformServiceVo;
import com.dome.bean.vo.TSPlatformServiceVo;
import com.dome.web.sysplatformservice.vo.CmsDoctorServiceVo;


/**
 * 平台服务数据访问层
 * @author admin
 *
 */
public interface TSPlatformServiceMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(TSPlatformService record);

    int insertSelective(TSPlatformService record);

    TSPlatformService selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TSPlatformService record);

    int updateByPrimaryKey(TSPlatformService record);

    /**
	 * 查询医生服务Vo
	 * @param parameter
	 * @return
	 */
	List<TSPlatformServiceVo> findDoctorServicesPage(Map<String, Object> parameter);
	
	/**
	 * 查询医生 栏目 服务
	 * @param par
	 * @return
	 */
	List<CmsDoctorServiceVo> findDoctorPlatformService(Map<String, Object> par);
	
	/**
	 * 查询平台服务
	 * @param par
	 * @return
	 */
	List<PlatformServiceVo> findPlatformServiceInfo(Map<String, Object> par);
}