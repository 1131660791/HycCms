package com.dome.web.cmscolumntype.dao;

import java.util.List;

import com.dome.bean.CmsColumnType;

/**
 * 栏目类型数据访问层
 * @author admin
 */
public interface CmsColumnTypeMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(CmsColumnType record);

    int insertSelective(CmsColumnType record);

    CmsColumnType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsColumnType record);

    int updateByPrimaryKey(CmsColumnType record);
    
    /**
     * 查询所有栏目类型
     * @return
     */
	List<CmsColumnType> findAllColumnType();

	/**
	 * 查询banner 内链 栏目类型
	 */
	List<CmsColumnType> findBannerInternalColumnType();
}