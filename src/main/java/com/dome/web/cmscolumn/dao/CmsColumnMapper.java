package com.dome.web.cmscolumn.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dome.bean.CmsColumn;
import com.dome.bean.vo.CmsColumnVo;

public interface CmsColumnMapper {
    int deleteByPrimaryKey(String id);

    int insert(CmsColumn record);

    int insertSelective(CmsColumn record);

    CmsColumn selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsColumn record);

    int updateByPrimaryKey(CmsColumn record);
    /**
     * 查询栏目vo
     * @param columId
     * @return
     */
	CmsColumnVo findColumnById(String columId);
	
	/**
	 * 查询医院栏目
	 * @param parameter
	 *  		platformId - 平台 id
	 *  		hospitalId - 医院 id
	 */
	List<CmsColumnVo> findHospitalColumn(Map<String, Object> parameter);
	
	/**
	 * 查询新闻类型栏目
	 * @return
	 */
	List<CmsColumn> findColumnByNews(Map<String,Object> parameter);

	/**
	 * 查询平台,机构的栏目
	 * @param parameter
	 * @return
	 */
	List<CmsColumn> findPlatformHospitalByColumn(Map<String, Object> parameter);

	/**
	 * 根据栏目平台id 医院id 栏目类型 查询栏目
	 * @param parameter
	 * @return
	 */
	List<CmsColumn> findPHColumnTypeByColumn(Map<String, Object> parameter);
	
	/**
	 * 远程查看 - 查询  医院 栏目  
	 * @param par
	 * @return
	 */
	List<CmsColumnVo> findRemoteColumns(Map<String, Object> par);
	
	/**
	 * 删除平台默认栏目
	 * @param parameter
	 * @return
	 */
	int deletePlatformDefaultColumn(Map<String, Object> parameter);
	
	/**
	 * 根据栏目类型查询栏目
	 * @param parameter
	 * @return
	 */
	List<CmsColumn> findColumnByType(Map<String, Object> parameter);
	
	/**
	 * 验证栏目Code 是否存在
	 * @param parameter
	 * @return
	 */
	List<CmsColumn> verifyColumnCode(Map<String, Object> parameter);
	
	/**
	 * 查询栏目子栏目
	 * @param columId - 栏目id
	 * @return
	 */
	List<CmsColumn> findSonColumn(Map<String, Object> parameter);

}