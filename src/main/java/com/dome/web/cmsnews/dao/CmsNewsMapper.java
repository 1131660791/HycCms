package com.dome.web.cmsnews.dao;

import java.util.List;
import java.util.Map;

import com.dome.bean.CmsNews;
import com.dome.bean.vo.CmsNewsVo;

public interface CmsNewsMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(CmsNews record);

    int insertSelective(CmsNews record);

    CmsNews selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsNews record);

    int updateByPrimaryKeyWithBLOBs(CmsNews record);

    int updateByPrimaryKey(CmsNews record);
    /**
     * 查询新闻
     * @param parameter
     * @return
     */
	CmsNewsVo findNews(Map<String, Object> parameter);
	/**
	 * 查询新闻列表
	 * @param parameter
	 * @return
	 */
	List<CmsNewsVo> findNewsListPage(Map<String, Object> parameter);
	
	/**
	 * 查询banner 新闻
	 * @param parameter
	 * @return
	 */
	List<CmsNews> findBannerNewsPage(Map<String, Object> parameter);
	
	/**
	 * 远程调用 - 新闻列表 -不分页
	 * @param par
	 * @return
	 */
	List<CmsNews> findRemoteNews(Map<String, Object> par);
	
	/**
	 * 远程调用 - 新闻列表 -分页
	 * @param par
	 * @return
	 */
	List<CmsNews> findRemoteNewsPage(Map<String, Object> par);
	
	/**
	 * 根据栏目id 查询栏目下的新闻
	 * @param par
	 * @return
	 */
	List<CmsNews> findColumnNewsByColumId(Map<String, Object> par);
}