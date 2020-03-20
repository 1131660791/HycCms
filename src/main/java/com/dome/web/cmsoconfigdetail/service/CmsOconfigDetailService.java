package com.dome.web.cmsoconfigdetail.service;

import com.dome.bean.CmsOconfigDetail;

public interface CmsOconfigDetailService {
	
    int deleteByPrimaryKey(String id);

    int insert(CmsOconfigDetail record);

    int insertSelective(CmsOconfigDetail record);

    CmsOconfigDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsOconfigDetail record);

    int updateByPrimaryKey(CmsOconfigDetail record);
}
