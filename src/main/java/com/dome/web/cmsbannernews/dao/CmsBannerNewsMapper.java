package com.dome.web.cmsbannernews.dao;

import java.util.List;
import java.util.Map;

import com.dome.bean.CmsBannerNews;

public interface CmsBannerNewsMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(CmsBannerNews record);

    int insertSelective(CmsBannerNews record);

    CmsBannerNews selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsBannerNews record);

    int updateByPrimaryKey(CmsBannerNews record);

	int deleteBannerNews(Map<String, Object> param);
	/**
	 * 
	 * @param p
	 * @return
	 */
	List<CmsBannerNews> selectBannerNews(Map<String, Object> p);
	
	/**
	 * 删除视频banner 关联 
	 * @param param
	 * @return
	 */
	int deleteBannerVideo(Map<String, Object> param);
	
	/**
	 * 校验banner 是否被关联
	 * @param parameter
	 * @return
	 */
	Map<String,Object> verifyBannerAssociatedFlag(Map<String, Object> parameter);
}