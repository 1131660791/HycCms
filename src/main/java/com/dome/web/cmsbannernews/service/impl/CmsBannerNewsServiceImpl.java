package com.dome.web.cmsbannernews.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.bean.CmsBannerNews;
import com.dome.web.cmsbannernews.dao.CmsBannerNewsMapper;
import com.dome.web.cmsbannernews.service.CmsBannerNewsService;

/**
 * banner新闻关联业务层
 */
@Service("bannerNewsService")
public class CmsBannerNewsServiceImpl implements CmsBannerNewsService{

	
	@Autowired
	private CmsBannerNewsMapper bannerNewsMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return bannerNewsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CmsBannerNews record) {
		// TODO Auto-generated method stub
		return bannerNewsMapper.insert(record);
	}

	@Override
	public int insertSelective(CmsBannerNews record) {
		// TODO Auto-generated method stub
		return bannerNewsMapper.insertSelective(record);
	}

	@Override
	public CmsBannerNews selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return bannerNewsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CmsBannerNews record) {
		// TODO Auto-generated method stub
		return bannerNewsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CmsBannerNews record) {
		// TODO Auto-generated method stub
		return bannerNewsMapper.updateByPrimaryKey(record);
	}

	@Override
	public Map<String,Object> verifyBannerAssociatedFlag(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return bannerNewsMapper.verifyBannerAssociatedFlag(parameter);
	}
	

}
