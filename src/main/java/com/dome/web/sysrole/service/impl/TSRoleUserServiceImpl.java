package com.dome.web.sysrole.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dome.bean.TSRoleUser;
import com.dome.web.sysrole.dao.TSRoleUserMapper;
import com.dome.web.sysrole.service.TSRoleUserService;

@Service("tSRoleUserService")
public class TSRoleUserServiceImpl implements TSRoleUserService{
	
	@Autowired
	private TSRoleUserMapper tSRoleUserMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return tSRoleUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(TSRoleUser record) {
		// TODO Auto-generated method stub
		return tSRoleUserMapper.insert(record);
	}

	@Override
	public int insertSelective(TSRoleUser record) {
		// TODO Auto-generated method stub
		return tSRoleUserMapper.insertSelective(record);
	}

	@Override
	public TSRoleUser selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return tSRoleUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TSRoleUser record) {
		// TODO Auto-generated method stub
		return tSRoleUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TSRoleUser record) {
		// TODO Auto-generated method stub
		return tSRoleUserMapper.updateByPrimaryKey(record);
	}

}
