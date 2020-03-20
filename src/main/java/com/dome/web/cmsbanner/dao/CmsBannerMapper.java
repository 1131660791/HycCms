package com.dome.web.cmsbanner.dao;

import java.util.List;
import java.util.Map;

import com.dome.bean.CmsBanner;
import com.dome.bean.vo.CmsBannerVo;
import com.dome.web.cmsbanner.vo.ColumnBannerVo;

public interface CmsBannerMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(CmsBanner record);

    int insertSelective(CmsBanner record);

    CmsBanner selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsBanner record);

    int updateByPrimaryKey(CmsBanner record);
    
    /**
     * 查询banner
     * @param parameter
     * @return
     */
	CmsBannerVo findBanner(Map<String, Object> parameter);
	
	/**
	 * 查询栏目banner
	 * @param parameter
	 * @return
	 */
	List<ColumnBannerVo> findColumnBanners(Map<String, Object> parameter);

	/**
	 * 查询远程调用 -banner
	 * @param par
	 * @return
	 */
	List<Map<String, Object>> findBannersRemote(Map<String, Object> par);
	
	/**
	 * 根据栏目id 查询栏目的banner
	 * @param par
	 * @return
	 */
	List<CmsBanner> findColumnBannerByColumId(Map<String, Object> par);
}