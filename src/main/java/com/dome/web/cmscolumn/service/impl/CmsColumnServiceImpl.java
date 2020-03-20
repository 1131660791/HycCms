package com.dome.web.cmscolumn.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dome.bean.CmsColumn;
import com.dome.bean.CmsDefaultColumn;
import com.dome.bean.vo.CmsColumnVo;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.uuid.UUIDUtil;
import com.dome.web.cmscolumn.dao.CmsColumnMapper;
import com.dome.web.cmscolumn.service.CmsColumnService;
import com.dome.web.cmsdefaultcolumn.dao.CmsDefaultColumnMapper;

@Service("columnService")
public class CmsColumnServiceImpl implements CmsColumnService{

	@Autowired
	private CmsColumnMapper cmsColumnMapper;
	
	@Autowired
	private CmsDefaultColumnMapper defaultColumnMapper;
	
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return cmsColumnMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CmsColumn record) {
		// TODO Auto-generated method stub
		return cmsColumnMapper.insert(record);
	}

	@Override
	public int insertSelective(CmsColumn record) {
		// TODO Auto-generated method stub
		return cmsColumnMapper.insertSelective(record);
	}

	@Override
	public CmsColumn selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return cmsColumnMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CmsColumn record) {
		// TODO Auto-generated method stub
		return cmsColumnMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CmsColumn record) {
		// TODO Auto-generated method stub
		return cmsColumnMapper.updateByPrimaryKey(record);
	}

	@Override
	public CmsColumnVo findColumnById(String columId) {
		// TODO Auto-generated method stub
		return cmsColumnMapper.findColumnById(columId);
	}
	
	@Override
	public void sortColumn(List<CmsColumn> collection) {
		// TODO Auto-generated method stub
		for(int i=0;i<collection.size();i++) {
			CmsColumn column = collection.get(i);
			cmsColumnMapper.updateByPrimaryKeySelective(column);
		}
	}

	/**
	 * 查询医院栏目
	 * @param parameter
	 *  	platformId - 平台 id
	 *  	hospitalId - 医院 id
	 */
	@Override
	public List<CmsColumnVo> findHospitalColumn(Map<String, Object> parameter) {
		List<CmsColumnVo> hospitalColumns = cmsColumnMapper.findHospitalColumn(parameter);
		 // 最后的结果
	    List<CmsColumnVo> columnVos = new ArrayList<CmsColumnVo>();
	    // 先找到所有的一级菜单
	    for (int i = 0; i < hospitalColumns.size(); i++) {
	        // 一级菜单没有 parentId
	        if (!StringUtils.hasText(hospitalColumns.get(i).getpId())) {
	        	columnVos.add(hospitalColumns.get(i)); 
	        }
	    }
	    
	    // 为一级菜单设置子菜单，getChild 是递归调用的
	    for (CmsColumnVo cunctionVo : columnVos) {
	    	cunctionVo.setCmsColumnVos(getChild(cunctionVo.getColumnId(), hospitalColumns));
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
	private List<CmsColumnVo> getChild(String id, List<CmsColumnVo> columns) {
	    // 子菜单
	    List<CmsColumnVo> childList = new ArrayList<>();
	    for (CmsColumnVo function : columns) {
	        // 遍历所有节点，将父菜单id与传过来的id比较
	        if (StringUtils.hasText(function.getpId())) {
	            if (function.getpId().equals(id)) {
	                childList.add(function);
	            }
	        }
	    }
	    
	    // 把子菜单的子菜单再循环一遍
	    for (CmsColumnVo columnVo : childList) {// 没有url子菜单还有子菜单
	        // 递归
	    	columnVo.setCmsColumnVos(getChild(columnVo.getColumnId(), columns));
	    } // 递归退出条件
	    if (childList.size() == 0) {
	        return null;
	    }
	    return childList;
	}

	@Override
	public List<CmsColumn> findColumnByNews(Map<String,Object> parameter) {
		// TODO Auto-generated method stub
		return cmsColumnMapper.findColumnByNews(parameter);
	}

	@Override
	public List<CmsColumn> findPlatformHospitalByColumn(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return cmsColumnMapper.findPlatformHospitalByColumn(parameter);
	}

	@Override
	public List<CmsColumn> findPHColumnTypeByColumn(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return cmsColumnMapper.findPHColumnTypeByColumn(parameter);
	}
	
	@Override
	public List<CmsColumnVo> findRemoteColumns(Map<String, Object> par) {
		List<CmsColumnVo> columns = cmsColumnMapper.findRemoteColumns(par);
		// 最后的结果
	    List<CmsColumnVo> columnVos = new ArrayList<CmsColumnVo>();
	    // 先找到所有的一级菜单
	    for (int i = 0; i < columns.size(); i++) {
	        // 一级菜单没有parentId
	        if (!StringUtils.hasText(columns.get(i).getpId())) {
	        	columnVos.add(columns.get(i));
	        }
	    }
	    
	    // 为一级菜单设置子菜单，getChild 是递归调用的
	    for (CmsColumnVo cunctionVo : columnVos) {
	    	cunctionVo.setCmsColumnVos(getChild(cunctionVo.getColumnId(), columns));
	    }
		return columnVos;
	}

	@Override
	public int importDefaultColumn(Map<String, Object> parameter) {
		// 删除平台原有默认栏目
		cmsColumnMapper.deletePlatformDefaultColumn(parameter);
		// 查询平台默认栏目
		List<CmsDefaultColumn> defaultColumns= defaultColumnMapper.findPlatformDefaultColumns(parameter);
		String userId = parameter.get("userId").toString();
		String userName = parameter.get("userName").toString();
		String hospitalId = parameter.get("hospitalId").toString();
		String departname = parameter.get("departname").toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		CmsColumn column = null;
		for (CmsDefaultColumn cmsDefaultColumn : defaultColumns) {
			column = new CmsColumn();
			column.setId(UUIDUtil.getUUID());
			column.setColumnId(cmsDefaultColumn.getId()); // 默认栏目 - 栏目id
			column.setpId(cmsDefaultColumn.getpId());
			column.setColumnName(cmsDefaultColumn.getColumnName());
			column.setColumnProperty(1);
			column.setFkColumnTypeId(cmsDefaultColumn.getFkColumnType());
			column.setState(0);
			column.setColumnCode(cmsDefaultColumn.getColumnCode());
			column.setColumnLink(cmsDefaultColumn.getColumnLink());
			column.setColumnUrl(cmsDefaultColumn.getColumnUrl());
			column.setSort(cmsDefaultColumn.getSort());
			column.setFkPlatformId(cmsDefaultColumn.getFkPlatformId());
			column.setHospitalId(hospitalId);
			column.setDepartname(departname);
			column.setCreateUser(userId);
			column.setCreateUserName(userName);
			column.setUpdateUser(userId);
			column.setUpdateUserName(userName);
			column.setCreateTime(time);
			column.setUpdateTime(time);
			
			cmsColumnMapper.insertSelective(column);
		}
		return 0;
	}

	@Override
	public List<CmsColumn> findColumnByType(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return cmsColumnMapper.findColumnByType(parameter);
	}

	@Override
	public int verifyColumnCode(Map<String, Object> parameter) {
		List<CmsColumn> columns = cmsColumnMapper.verifyColumnCode(parameter);
		if(columns !=null && columns.size() >0) {
			return 1;
		}else {
			return 0;
		}
	}

	@Override
	public List<CmsColumn> findSonColumn(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return cmsColumnMapper.findSonColumn(parameter);
	}

}
