package com.dome.web.cmsoconfigdetail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.bean.CmsOconfigDetail;
import com.dome.web.cmsoconfigdetail.dao.CmsOconfigDetailMapper;
import com.dome.web.cmsoconfigdetail.service.CmsOconfigDetailService;

@Service("oconfigDetailService")
public class CmsOconfigDetailServiceImpl implements CmsOconfigDetailService{

	@Autowired
	private CmsOconfigDetailMapper oconfigDetailMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return oconfigDetailMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CmsOconfigDetail record) {
		// TODO Auto-generated method stub
		return oconfigDetailMapper.insert(record);
	}

	@Override
	public int insertSelective(CmsOconfigDetail record) {
		// TODO Auto-generated method stub
		return oconfigDetailMapper.insertSelective(record);
	}

	@Override
	public CmsOconfigDetail selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return oconfigDetailMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CmsOconfigDetail record) {
		// TODO Auto-generated method stub
		return oconfigDetailMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CmsOconfigDetail record) {
		// TODO Auto-generated method stub
		return oconfigDetailMapper.updateByPrimaryKey(record);
	}
	
	
}
