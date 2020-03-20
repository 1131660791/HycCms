package com.dome.web.sysdepart.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.bean.TSDepart;
import com.dome.web.sysdepart.dao.TSDepartMapper;
import com.dome.web.sysdepart.service.TSDepartService;

@Service("departService")
public class TSDepartServiceImpl implements TSDepartService{

	@Autowired
	private TSDepartMapper departMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return departMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(TSDepart record) {
		// TODO Auto-generated method stub
		return departMapper.insert(record);
	}

	@Override
	public int insertSelective(TSDepart record) {
		// TODO Auto-generated method stub
		return departMapper.insertSelective(record);
	}

	@Override
	public TSDepart selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return departMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TSDepart record) {
		// TODO Auto-generated method stub
		return departMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(TSDepart record) {
		// TODO Auto-generated method stub
		return departMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(TSDepart record) {
		// TODO Auto-generated method stub
		return departMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<TSDepart> findUserDeparts(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return departMapper.findUserDeparts(parameter);
	}

	@Override
	public List<TSDepart> findTopDepartsPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return departMapper.findTopDepartsPage(parameter);
	}

	@Override
	public List<TSDepart> findHospitalSection(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return departMapper.findHospitalSection(parameter);
	}
	

}
