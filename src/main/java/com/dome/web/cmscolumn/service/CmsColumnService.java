package com.dome.web.cmscolumn.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dome.bean.CmsColumn;
import com.dome.bean.vo.CmsColumnVo;

/**
 * 栏目业务接口层
 * 
 * @author admin
 */
public interface CmsColumnService {

	int deleteByPrimaryKey(String id);

	int insert(CmsColumn record);

	int insertSelective(CmsColumn record);

	CmsColumn selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(CmsColumn record);

	int updateByPrimaryKey(CmsColumn record);

	/**
	 * 查询栏目vo
	 * 
	 * @param columId
	 * @return
	 */
	CmsColumnVo findColumnById(String columId);

	/**
	 * pa
	 * 
	 * @param objectCollection
	 * @return
	 */
	void sortColumn(List<CmsColumn> collection);

	/**
	 * 查询医院栏目
	 * 
	 * @param parameter
	 *            platformId - 平台 id hospitalId - 医院 id
	 */
	List<CmsColumnVo> findHospitalColumn(Map<String, Object> parameter);

	/**
	 * 查询新闻类型栏目
	 * 
	 * @return
	 */
	List<CmsColumn> findColumnByNews(Map<String, Object> parameter);

	/**
	 * 查询平台,机构的栏目
	 * 
	 * @param parameter
	 * @return
	 */
	List<CmsColumn> findPlatformHospitalByColumn(Map<String, Object> parameter);

	/**
	 * 根据栏目平台id 医院id 栏目类型 查询栏目
	 * 
	 * @param parameter
	 * @return
	 */
	List<CmsColumn> findPHColumnTypeByColumn(Map<String, Object> parameter);

	/**
	 * 远程查看 - 查询 医院 栏目
	 * 
	 * @param par
	 * @return
	 */
	List<CmsColumnVo> findRemoteColumns(Map<String, Object> par);

	/**
	 * 导入默认栏目
	 * 
	 * @param parameter
	 * @return
	 */
	int importDefaultColumn(Map<String, Object> parameter);

	/**
	 * 查询视频类型 栏目
	 * 
	 * @param parameter
	 * @return
	 */
	List<CmsColumn> findColumnByType(Map<String, Object> parameter);
	
	/**
	 * 验证栏目code是否存在
	 * @param parameter
	 * @return
	 */
	int verifyColumnCode(Map<String, Object> parameter);
	
	/**
	 * 查询栏目下 的子栏目
	 * @param columId
	 * @return
	 */
	List<CmsColumn> findSonColumn(Map<String, Object> parameter);

}
