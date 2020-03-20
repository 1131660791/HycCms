package com.dome.web.cmsnews.service;

import java.util.List;
import java.util.Map;

import com.dome.bean.CmsColumn;
import com.dome.bean.CmsNews;
import com.dome.bean.vo.CmsNewsVo;

import net.sf.json.JSONArray;

public interface CmsNewsService {
	
    int deleteByPrimaryKey(String id);

    int insert(CmsNews record);

    int insertSelective(CmsNews record);

    CmsNews selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsNews record);

    int updateByPrimaryKeyWithBLOBs(CmsNews record);

    int updateByPrimaryKey(CmsNews record);
    /**
     * 添加新闻
     * @param cmsNews
     * @return
     */
	int addNews(CmsNews cmsNews);
	/**
	 * 删除新闻
	 * @param parameter
	 * @return
	 */
	int deleteNews(Map<String, Object> parameter);
	/**
	 * 查看新闻
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
	 * 新闻id json数组
	 * @param newsIdArray
	 * @return
	 */
	int publishNews(JSONArray newsIdArray);

	/**
	 * 转载新闻
	 * @param parameter
	 * @return
	 */
	int reshipmentNews(Map<String, Object> parameter);

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
	 * 远程调用 - 新闻列表 - 分页
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
