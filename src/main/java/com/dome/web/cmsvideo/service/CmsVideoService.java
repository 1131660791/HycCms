package com.dome.web.cmsvideo.service;

import java.util.List;
import java.util.Map;

import com.dome.bean.CmsVideo;
import com.dome.bean.vo.CmsVideoVo;

public interface CmsVideoService {

    int deleteByPrimaryKey(String id);

    int insert(CmsVideo record);

    int insertSelective(CmsVideo record);

    CmsVideo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsVideo record);

    int updateByPrimaryKey(CmsVideo record);
    
    /**
     * 发布视频
     * @param videoIdJson
     * @return
     */
	int publishVideo(String videoIdJson);
	
	/**
	 * 批量转载
	 * @param videoJson
	 * @return
	 */
	int reshipmentVideo(Map<String, Object> parameter);
	
	/**
	 * 查询视频分页
	 * @param parameter
	 * @return
	 */
	List<CmsVideoVo> findVideosPage(Map<String, Object> parameter);
	
	/**
	 *  查询视频类型栏目 的视频列表
	 * @param parameter
	 * @return
	 */
	List<CmsVideo> findVideoColumnVideosPage(Map<String, Object> parameter);
	
	/**
	 * 删除视频
	 * @param par
	 * @return
	 */
	int deleteVideo(Map<String, Object> par);
	
	/**
	 * 远程查看视频列表
	 * @param par
	 * @return
	 */
	List<CmsVideo> findRemoteVideosPage(Map<String, Object> par);
	
	/**
	 * 远程查看视频详情
	 * @param parameter
	 * @return
	 */
	CmsVideo findVideoRemote(Map<String, Object> parameter);
}
