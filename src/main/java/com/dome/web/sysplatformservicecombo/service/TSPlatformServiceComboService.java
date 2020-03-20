package com.dome.web.sysplatformservicecombo.service;

import com.dome.bean.TSPlatformServiceCombo;

public interface TSPlatformServiceComboService {
	
    int deleteByPrimaryKey(String id);

    int insert(TSPlatformServiceCombo record);

    int insertSelective(TSPlatformServiceCombo record);

    TSPlatformServiceCombo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TSPlatformServiceCombo record);

    int updateByPrimaryKey(TSPlatformServiceCombo record);
}
