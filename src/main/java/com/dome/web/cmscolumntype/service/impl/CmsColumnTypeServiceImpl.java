package com.dome.web.cmscolumntype.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dome.bean.CmsColumnType;
import com.dome.web.cmscolumntype.dao.CmsColumnTypeMapper;
import com.dome.web.cmscolumntype.service.CmsColumnTypeService;

/**
 * 栏目类型服务层
 * @author admin
 */
@Service("columnTypeService")
public class CmsColumnTypeServiceImpl implements CmsColumnTypeService{

	@Autowired
	private CmsColumnTypeMapper columnTypeMapper;
	
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return columnTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CmsColumnType record) {
		// TODO Auto-generated method stub
		return columnTypeMapper.insert(record);
	}

	@Override
	public int insertSelective(CmsColumnType record) {
		// TODO Auto-generated method stub
		return columnTypeMapper.insertSelective(record);
	}

	@Override
	public CmsColumnType selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return columnTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CmsColumnType record) {
		// TODO Auto-generated method stub
		return columnTypeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CmsColumnType record) {
		// TODO Auto-generated method stub
		return columnTypeMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<CmsColumnType> findAllColumnType() {
		// TODO Auto-generated method stub
		return columnTypeMapper.findAllColumnType();
	}

	@Override
	public List<CmsColumnType> findBannerInternalColumnType() {
		// TODO Auto-generated method stub
		return columnTypeMapper.findBannerInternalColumnType();
	}

}
