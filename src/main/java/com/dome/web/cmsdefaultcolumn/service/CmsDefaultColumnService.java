package com.dome.web.cmsdefaultcolumn.service;

import java.util.List;
import java.util.Map;

import com.dome.bean.CmsDefaultColumn;
import com.dome.bean.vo.CmsDefaultColumnVo;

public interface CmsDefaultColumnService {
	
    int deleteByPrimaryKey(String id);

    int insert(CmsDefaultColumn record);

    int insertSelective(CmsDefaultColumn record);

    CmsDefaultColumn selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsDefaultColumn record);

    int updateByPrimaryKey(CmsDefaultColumn record);
    /**
     * 查询平台默认栏目
     * @param parameter
     * @return
     */
	List<CmsDefaultColumnVo> findDefaultColumns(Map<String, Object> parameter);
	/**
	 *  根据栏目平台id
	 * @param platformId  - 平台Id
	 * @param columnId	  - 栏目Id
	 * @param response
	 */
	CmsDefaultColumnVo findDefaultColumnsById(Map<String, Object> parameter);
	
	/**
	 * 默认栏目排序
	 * @param collection
	 * @return
	 */
	int sortDefaultColumn(List<CmsDefaultColumn> collection);
	
	/**
	 * 
	 * @param parameter
	 * @return
	 */
	int verifyDefaultColumnCode(Map<String, Object> parameter);
	
	/**
	 * 查询默认栏目 子栏目
	 * @param defaultColumnId
	 * @return
	 */
	List<CmsDefaultColumn> findSonDefaultColumn(String defaultColumnId);
	
	
	
}
