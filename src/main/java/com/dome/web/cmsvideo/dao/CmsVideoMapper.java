package com.dome.web.cmsvideo.dao;

import java.util.List;
import java.util.Map;

import com.dome.bean.CmsVideo;
import com.dome.bean.vo.CmsVideoVo;

public interface CmsVideoMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(CmsVideo record);

    int insertSelective(CmsVideo record);

    CmsVideo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsVideo record);

    int updateByPrimaryKey(CmsVideo record);
    
    /**
     * 查询视频 列表 分页
     * @param parameter
     * @return
     */
	List<CmsVideoVo> findVideosPage(Map<String, Object> parameter);
	
	/**
	 * 查询视频类型栏目 的视频列表
	 * @param parameter
	 * @return
	 */
	List<CmsVideo> findVideoColumnVideosPage(Map<String, Object> parameter);
	
	/**
	 * 远程查看视频列表
	 * @param par
	 * @return
	 */
	List<CmsVideo> findRemoteVideosPage(Map<String, Object> par);
	
	/**
	 * 
	 */
	CmsVideo findVideoRemote(Map<String, Object> parameter);
}