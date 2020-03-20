package com.dome.web.cmsbanner.dao;

import java.util.List;
import java.util.Map;

import com.dome.bean.CmsBannerContent;

public interface CmsBannerContentMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(CmsBannerContent record);

    int insertSelective(CmsBannerContent record);

    CmsBannerContent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsBannerContent record);

    int updateByPrimaryKey(CmsBannerContent record);
    
    /**
     * 删除banner 内容 根据bannerId
     * @param param
     * @return
     */
	int deleteBannerContent(Map<String, Object> param);

	/**
	 * 查询banner 内容 
	 * @param par
	 * @return
	 */
	List<CmsBannerContent> findBannerBannerContent(Map<String, Object> par);
}