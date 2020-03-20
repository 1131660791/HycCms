package com.dome.web.sysuser.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.bean.TSBaseUser;
import com.dome.web.sysuser.dao.TSBaseUserMapper;
import com.dome.web.sysuser.service.TSBaseUserService;

@Service("tSBaseUserService")
public class TSBaseUserServiceImpl implements TSBaseUserService{
	
	@Autowired
	private TSBaseUserMapper baseUserMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return baseUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(TSBaseUser record) {
		// TODO Auto-generated method stub
		return baseUserMapper.insert(record);
	}

	@Override
	public int insertSelective(TSBaseUser record) {
		// TODO Auto-generated method stub
		return baseUserMapper.insertSelective(record);
	}

	@Override
	public TSBaseUser selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return baseUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TSBaseUser record) {
		// TODO Auto-generated method stub
		return baseUserMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public int updateByPrimaryKeyWithBLOBs(TSBaseUser record) {
		// TODO Auto-generated method stub
		return baseUserMapper.updateByPrimaryKeyWithBLOBs(record);
	}
	
	@Override
	public int updateByPrimaryKey(TSBaseUser record) {
		// TODO Auto-generated method stub
		return baseUserMapper.updateByPrimaryKey(record);
	}

	@Override
	public TSBaseUser findBaseUserInfo(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return baseUserMapper.findBaseUserInfo(parameter);
	}
	
	
	
}
