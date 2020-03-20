package com.dome.web.cmsbannernews.service;

import java.util.List;
import java.util.Map;

import com.dome.bean.CmsBannerNews;

public interface CmsBannerNewsService {
	
    int deleteByPrimaryKey(String id);

    int insert(CmsBannerNews record);

    int insertSelective(CmsBannerNews record);

    CmsBannerNews selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsBannerNews record);

    int updateByPrimaryKey(CmsBannerNews record);
    
    /**
     * 校验banner 是否被关联
     * @param parameter
     * @return
     */
    Map<String,Object> verifyBannerAssociatedFlag(Map<String, Object> parameter);
}
