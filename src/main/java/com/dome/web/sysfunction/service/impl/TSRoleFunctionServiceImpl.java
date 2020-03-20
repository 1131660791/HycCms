package com.dome.web.sysfunction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.bean.TSRoleFunction;
import com.dome.web.sysfunction.dao.TSRoleFunctionMapper;
import com.dome.web.sysfunction.service.TSRoleFunctionService;

@Service("tSRoleFunctionService")
public class TSRoleFunctionServiceImpl implements TSRoleFunctionService{
	
	@Autowired
	private TSRoleFunctionMapper tsRoleFunctionMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return tsRoleFunctionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(TSRoleFunction record) {
		// TODO Auto-generated method stub
		return tsRoleFunctionMapper.insert(record);
	}

	@Override
	public int insertSelective(TSRoleFunction record) {
		// TODO Auto-generated method stub
		return tsRoleFunctionMapper.insertSelective(record);
	}

	@Override
	public TSRoleFunction selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return tsRoleFunctionMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TSRoleFunction record) {
		// TODO Auto-generated method stub
		return tsRoleFunctionMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TSRoleFunction record) {
		// TODO Auto-generated method stub
		return tsRoleFunctionMapper.updateByPrimaryKey(record);
	}
	
}
