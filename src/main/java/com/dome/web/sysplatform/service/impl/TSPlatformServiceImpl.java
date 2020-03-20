package com.dome.web.sysplatform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.bean.TSPlatform;
import com.dome.web.sysplatform.dao.TSPlatformMapper;
import com.dome.web.sysplatform.service.TSPlatformService;

@Service("platformService")
public class TSPlatformServiceImpl implements TSPlatformService{

	@Autowired
	private TSPlatformMapper platformMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return platformMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(TSPlatform record) {
		// TODO Auto-generated method stub
		return platformMapper.insert(record);
	}

	@Override
	public int insertSelective(TSPlatform record) {
		// TODO Auto-generated method stub
		return platformMapper.insertSelective(record);
	}

	@Override
	public TSPlatform selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return platformMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TSPlatform record) {
		// TODO Auto-generated method stub
		return platformMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TSPlatform record) {
		// TODO Auto-generated method stub
		return platformMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<TSPlatform> findRolePlatforms(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return platformMapper.findRolePlatforms(parameter);
	}

	@Override
	public List<TSPlatform> findPlatformsPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return platformMapper.findPlatformsPage(parameter);
	}

	@Override
	public List<TSPlatform> findPlatformList() {
		// TODO Auto-generated method stub
		return platformMapper.findPlatformList();
	}

}
