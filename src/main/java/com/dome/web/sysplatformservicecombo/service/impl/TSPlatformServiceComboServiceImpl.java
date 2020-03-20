package com.dome.web.sysplatformservicecombo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.bean.TSPlatformServiceCombo;
import com.dome.web.sysplatformservicecombo.dao.TSPlatformServiceComboMapper;
import com.dome.web.sysplatformservicecombo.service.TSPlatformServiceComboService;

/**
 * 平台服务 套餐 业务层
 * @author admin
 *
 */
@Service("platformServiceComboService")
public class TSPlatformServiceComboServiceImpl implements TSPlatformServiceComboService{

	@Autowired
	private TSPlatformServiceComboMapper platformServiceComboMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return platformServiceComboMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(TSPlatformServiceCombo record) {
		// TODO Auto-generated method stub
		return platformServiceComboMapper.insert(record);
	}

	@Override
	public int insertSelective(TSPlatformServiceCombo record) {
		// TODO Auto-generated method stub
		return platformServiceComboMapper.insertSelective(record);
	}

	@Override
	public TSPlatformServiceCombo selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return platformServiceComboMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TSPlatformServiceCombo record) {
		// TODO Auto-generated method stub
		return platformServiceComboMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TSPlatformServiceCombo record) {
		// TODO Auto-generated method stub
		return platformServiceComboMapper.updateByPrimaryKey(record);
	}

}
