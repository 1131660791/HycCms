package com.dome.web.cmsdefaultcolumn.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dome.bean.CmsColumn;
import com.dome.bean.CmsDefaultColumn;
import com.dome.bean.vo.CmsColumnVo;
import com.dome.bean.vo.CmsDefaultColumnVo;
import com.dome.web.cmsdefaultcolumn.dao.CmsDefaultColumnMapper;
import com.dome.web.cmsdefaultcolumn.service.CmsDefaultColumnService;

@Service("defaultColumnService")
public class CmsDefaultColumnServiceImpl implements CmsDefaultColumnService{

	@Autowired
	private CmsDefaultColumnMapper defaultColumnMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return defaultColumnMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CmsDefaultColumn record) {
		// TODO Auto-generated method stub
		return defaultColumnMapper.insert(record);
	}

	@Override
	public int insertSelective(CmsDefaultColumn record) {
		// TODO Auto-generated method stub
		return defaultColumnMapper.insertSelective(record);
	}

	@Override
	public CmsDefaultColumn selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return defaultColumnMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CmsDefaultColumn record) {
		// TODO Auto-generated method stub
		return defaultColumnMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CmsDefaultColumn record) {
		// TODO Auto-generated method stub
		return defaultColumnMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<CmsDefaultColumnVo> findDefaultColumns(Map<String, Object> parameter) {
		
		List<CmsDefaultColumnVo> defaultColumnVos = defaultColumnMapper.findDefaultColumns(parameter);
		 // 最后的结果
	    List<CmsDefaultColumnVo> columnVos = new ArrayList<CmsDefaultColumnVo>();
	    // 先找到所有的一级菜单
	    for (int i = 0; i < defaultColumnVos.size(); i++) {
	        // 一级菜单没有parentId
	        if (!StringUtils.hasText(defaultColumnVos.get(i).getpId())) {
	        	columnVos.add(defaultColumnVos.get(i)); 
	        }
	    }
	    
	    // 为一级菜单设置子菜单，getChild 是递归调用的
	    for (CmsDefaultColumnVo cunctionVo : columnVos) {
	    	cunctionVo.setDefaultColumnVos(getChild(cunctionVo.getId(), defaultColumnVos));
	    }
		return columnVos;
	}
	
	
	/**
	 * 递归查找子菜单
	 * @param id
	 *            当前菜单id
	 * @param functions
	 *            要查找的列表
	 * @return
	 */
	private List<CmsDefaultColumnVo> getChild(String id, List<CmsDefaultColumnVo> columns) {
	    // 子菜单
	    List<CmsDefaultColumnVo> childList = new ArrayList<>();
	    for (CmsDefaultColumnVo defaultColumnVo : columns) {
	        // 遍历所有节点，将父菜单id与传过来的id比较
	        if (StringUtils.hasText(defaultColumnVo.getpId())) {
	            if (defaultColumnVo.getpId().equals(id)) {
	                childList.add(defaultColumnVo);
	            }
	        }
	    }
	    
	    // 把子菜单的子菜单再循环一遍
	    for (CmsDefaultColumnVo columnVo : childList) {// 没有url子菜单还有子菜单
	        // 递归
	    	columnVo.setDefaultColumnVos(getChild(columnVo.getId(), columns));
	    } // 递归退出条件
	    if (childList.size() == 0) {
	        return null;
	    }
	    return childList;
	}

	@Override
	public CmsDefaultColumnVo findDefaultColumnsById(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return defaultColumnMapper.findDefaultColumnsById(parameter);
	}

	@Override
	public int sortDefaultColumn(List<CmsDefaultColumn> collection) {
		// TODO Auto-generated method stub
		for(int i=0;i<collection.size();i++) {
			CmsDefaultColumn column = collection.get(i);
			defaultColumnMapper.updateByPrimaryKeySelective(column);
		}
		return 1;
	}

	@Override
	public int verifyDefaultColumnCode(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		List<CmsDefaultColumn> defaultColumns = defaultColumnMapper.verifyDefaultColumnCode(parameter);
		if(defaultColumns != null && defaultColumns.size() >0) {
			return 1;
		}else {
			return 0;
		}
	}

	@Override
	public List<CmsDefaultColumn> findSonDefaultColumn(String defaultColumnId) {
		// TODO Auto-generated method stub
		return defaultColumnMapper.findSonDefaultColumn(defaultColumnId);
	}

}
