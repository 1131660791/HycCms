package com.dome.web.cmsoconfigdetail.dao;

import java.util.Map;

import com.dome.bean.CmsOconfigDetail;

public interface CmsOconfigDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(CmsOconfigDetail record);

    int insertSelective(CmsOconfigDetail record);

    CmsOconfigDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsOconfigDetail record);

    int updateByPrimaryKey(CmsOconfigDetail record);
    
    /**
     * 删除医生服务 配置关联
     * @param param
     * @return
     */
	int deleteDoctorConfigDetail(Map<String, Object> param);
}