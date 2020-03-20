package com.dome.web.sysuser.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.bean.TSUser;
import com.dome.bean.vo.TSUserVo;
import com.dome.web.sysuser.dao.TSUserMapper;
import com.dome.web.sysuser.service.TSUserService;

@Service("tSUserService")
public class TSUserServiceImpl implements TSUserService{

	@Autowired
	private TSUserMapper tSUserMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return tSUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(TSUser record) {
		// TODO Auto-generated method stub
		return tSUserMapper.insert(record);
	}

	@Override
	public int insertSelective(TSUser record) {
		// TODO Auto-generated method stub
		return tSUserMapper.insertSelective(record);
	}

	@Override
	public TSUser selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return tSUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TSUser record) {
		// TODO Auto-generated method stub
		return tSUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TSUser record) {
		// TODO Auto-generated method stub
		return tSUserMapper.updateByPrimaryKey(record);
	}

	@Override
	public TSUserVo findSysUserInfo(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return tSUserMapper.findSysUserInfo(parameter);
	}
	
	/**
	 * 查询医生信息
	 */
	@Override
	public Map<String,Object> findDoctorInfo(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return tSUserMapper.findDoctorInfo(parameter);
	}

	@Override
	public List<TSUserVo> findUsersPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return tSUserMapper.findUsersPage(parameter);
	}



	
	
}
