package com.dome.web.cmsdefaultcolumn.dao;

import java.util.List;
import java.util.Map;

import com.dome.bean.CmsDefaultColumn;
import com.dome.bean.vo.CmsDefaultColumnVo;

public interface CmsDefaultColumnMapper {
	
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
	 * 根据栏目平台id
	 * @param parameter
	 * @return
	 */
	CmsDefaultColumnVo findDefaultColumnsById(Map<String, Object> parameter);
	
	/**
	 * 查询平台默认栏目
	 * @param parameter
	 * @return
	 */
	List<CmsDefaultColumn> findPlatformDefaultColumns(Map<String, Object> parameter);
	
	/**
	 * 验证栏目code是否已存在
	 * @param parameter
	 * @return
	 */
	List<CmsDefaultColumn> verifyDefaultColumnCode(Map<String, Object> parameter);
	
	/**
	 * 查询 默认栏目 子栏目
	 * @param defaultColumnId
	 * @return
	 */
	List<CmsDefaultColumn> findSonDefaultColumn(String defaultColumnId);
	
}