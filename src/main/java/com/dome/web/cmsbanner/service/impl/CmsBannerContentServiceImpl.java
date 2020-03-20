package com.dome.web.cmsbanner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dome.bean.CmsBannerContent;
import com.dome.web.cmsbanner.dao.CmsBannerContentMapper;
import com.dome.web.cmsbanner.service.CmsBannerContentService;

@Service("bannerContent")
public class CmsBannerContentServiceImpl implements CmsBannerContentService{

	@Autowired
	private CmsBannerContentMapper bannerContentMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return bannerContentMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CmsBannerContent record) {
		// TODO Auto-generated method stub
		return bannerContentMapper.insert(record);
	}

	@Override
	public int insertSelective(CmsBannerContent record) {
		// TODO Auto-generated method stub
		return bannerContentMapper.insertSelective(record);
	}

	@Override
	public CmsBannerContent selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return bannerContentMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CmsBannerContent record) {
		// TODO Auto-generated method stub
		return bannerContentMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CmsBannerContent record) {
		// TODO Auto-generated method stub
		return bannerContentMapper.updateByPrimaryKey(record);
	}

}
