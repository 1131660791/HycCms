package com.dome.web.cmsnews.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.bean.CmsBannerContent;
import com.dome.bean.CmsBannerNews;
import com.dome.bean.CmsNews;
import com.dome.bean.vo.CmsNewsVo;
import com.dome.util.uuid.UUIDUtil;
import com.dome.web.cmsbanner.dao.CmsBannerContentMapper;
import com.dome.web.cmsbannernews.dao.CmsBannerNewsMapper;
import com.dome.web.cmsnews.dao.CmsNewsMapper;
import com.dome.web.cmsnews.service.CmsNewsService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("newsService")
public class CmsNewsServiceImpl implements CmsNewsService{

	@Autowired
	private CmsNewsMapper newsMapper;
	
	@Autowired
	private CmsBannerNewsMapper bannerNewsMapper;
	
	@Autowired
	private CmsBannerContentMapper bannerContentMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return newsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CmsNews record) {
		// TODO Auto-generated method stub
		return newsMapper.insert(record);
	}

	@Override
	public int insertSelective(CmsNews record) {
		// TODO Auto-generated method stub
		return newsMapper.insertSelective(record);
	}

	@Override
	public CmsNews selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return newsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CmsNews record) {
		// TODO Auto-generated method stub
		return newsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(CmsNews record) {
		// TODO Auto-generated method stub
		return newsMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(CmsNews record) {
		// TODO Auto-generated method stub
		return newsMapper.updateByPrimaryKey(record);
	}

	@Override
	public int addNews(CmsNews cmsNews) {
		int reslut = newsMapper.insertSelective(cmsNews);
		return reslut;
	}

	@Override
	public int deleteNews(Map<String, Object> parameter) {
		String id = parameter.get("newsId").toString();
		int bannerFlag = Integer.parseInt(parameter.get("bannerFlag").toString());
		// 删除新闻与 banner 的关联
		if(bannerFlag == 1) {
			// 查询 新闻 是否关联 了 banner 如果有  将 关联 删掉, 将banner关联新闻状态改为未关联
			Map<String, Object> p = new HashMap();
			p.put("newsId", id);
			// 将此新闻关联的banner状态改为 未关联 新闻, 一个新闻可能关联多个 banner
			List<CmsBannerNews> bannerNews = bannerNewsMapper.selectBannerNews(p);
			if(bannerNews !=null && bannerNews.size() > 0) {
				CmsBannerContent bannerContent = null;
				for(int i=0;i<bannerNews.size();i++) {
					bannerContent = new CmsBannerContent();
					String bannerContentId = bannerNews.get(i).getFkBannerId();
					bannerContent.setId(bannerContentId);
					bannerContent.setQuoteFlag(0);
					bannerContentMapper.updateByPrimaryKeySelective(bannerContent);
				}
			}
			Map<String, Object> param = new HashMap();
			param.put("newsId", id);
			// 删除新闻banner 关联 
			bannerNewsMapper.deleteBannerNews(param);
		}
		// 删除新闻
		int reslut = newsMapper.deleteByPrimaryKey(id);
		return reslut;
	}
	
	@Override
	public CmsNewsVo findNews(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return newsMapper.findNews(parameter);
	}

	@Override
	public List<CmsNewsVo> findNewsListPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return newsMapper.findNewsListPage(parameter);
	}

	@Override
	public int publishNews(JSONArray newsIdArray) {
		// TODO Auto-generated method stub
		CmsNews news = null;
		for(int i =0;i<newsIdArray.size();i++) {
			String newsIdStr = newsIdArray.get(i).toString();
			JSONObject newsObj =JSONObject.fromObject(newsIdStr);
			news = new CmsNews();
			news.setId(newsObj.get("newsId").toString());
			news.setState(0); // 发布
			newsMapper.updateByPrimaryKeySelective(news);
		}
		return 1;
	}
	
	/**
	 * {
		 "platformId": "转载到的平台Id",
		 "hospitalId": "转载到的机构Id",
		 "columnId": "转载到的栏目Id",
		 "publicTime": "yyyy-MM-dd HH:mm:ss",
		 "expireTime": "yyyy-MM-dd HH:mm:ss",
		 "newsIds": [{
			"newsId": "转载的id值"
		 }, {
			"newsId": "转载的id值"
		}]
	  }
	 */
	@Override
	public int reshipmentNews(Map<String, Object> parameter) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		String newsJson = parameter.get("newsJson").toString();
		String userId = parameter.get("userId").toString();

		JSONObject newsObj = JSONObject.fromObject(newsJson);

		String platformId = newsObj.get("platformId").toString();
		String hospitalId = newsObj.get("hospitalId").toString();
		String columnId = newsObj.get("columnId").toString();

		String publicTime = newsObj.getString("publicTime");
		String expireTime = newsObj.getString("expireTime");

		JSONArray reshipments = newsObj.getJSONArray("newsIds"); // 转载信息

		for (int i = 0; i < reshipments.size(); i++) {
			String newsIdsStr = reshipments.get(i).toString();
			JSONObject newsIdObj = JSONObject.fromObject(newsIdsStr);
			String newsId = newsIdObj.getString("newsId");
			// 查询出对应新闻
			CmsNews news = newsMapper.selectByPrimaryKey(newsId);
			if (news != null) {
				news.setId(UUIDUtil.getUUID());
				news.setFkColumnId(columnId);
				news.setHospitalId(hospitalId);
				news.setFkPlatformId(platformId);
				news.setState(1); 
				news.setNewsArrposid(2);
				news.setPublicTime(publicTime);
				news.setExpireTime(expireTime);
				int reslut = newsMapper.insertSelective(news);
			}
		}
		return 1;
	}

	@Override
	public List<CmsNews> findBannerNewsPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return newsMapper.findBannerNewsPage(parameter);
	}

	@Override
	public List<CmsNews> findRemoteNews(Map<String, Object> par) {
		// TODO Auto-generated method stub
		return newsMapper.findRemoteNews(par);
	}

	@Override
	public List<CmsNews> findRemoteNewsPage(Map<String, Object> par) {
		// TODO Auto-generated method stub
		return newsMapper.findRemoteNewsPage(par);
	}

	@Override
	public List<CmsNews> findColumnNewsByColumId(Map<String, Object> par) {
		// TODO Auto-generated method stub
		return newsMapper.findColumnNewsByColumId(par);
	}

}
