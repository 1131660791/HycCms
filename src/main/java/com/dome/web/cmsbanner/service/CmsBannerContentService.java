package com.dome.web.cmsbanner.service;

import com.dome.bean.CmsBannerContent;

public interface CmsBannerContentService {
	
    int deleteByPrimaryKey(String id);

    int insert(CmsBannerContent record);

    int insertSelective(CmsBannerContent record);

    CmsBannerContent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsBannerContent record);

    int updateByPrimaryKey(CmsBannerContent record);
}
