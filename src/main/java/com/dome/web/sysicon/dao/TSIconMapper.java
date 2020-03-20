package com.dome.web.sysicon.dao;

import com.dome.bean.TSIcon;

public interface TSIconMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(TSIcon record);

    int insertSelective(TSIcon record);

    TSIcon selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TSIcon record);

    int updateByPrimaryKeyWithBLOBs(TSIcon record);

    int updateByPrimaryKey(TSIcon record);
}