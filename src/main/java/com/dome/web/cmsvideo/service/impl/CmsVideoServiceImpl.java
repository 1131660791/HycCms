package com.dome.web.cmsvideo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.bean.CmsBannerContent;
import com.dome.bean.CmsBannerNews;
import com.dome.bean.CmsVideo;
import com.dome.bean.vo.CmsVideoVo;
import com.dome.util.uuid.UUIDUtil;
import com.dome.web.cmsbanner.dao.CmsBannerContentMapper;
import com.dome.web.cmsbannernews.dao.CmsBannerNewsMapper;
import com.dome.web.cmsvideo.dao.CmsVideoMapper;
import com.dome.web.cmsvideo.service.CmsVideoService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("videoService")
public class CmsVideoServiceImpl implements CmsVideoService{

	@Autowired
	private CmsVideoMapper videoMapper;
	
	@Autowired
	private CmsBannerNewsMapper bannerNewsMapper; 
	
	@Autowired
	private CmsBannerContentMapper bannerContentMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return videoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CmsVideo record) {
		// TODO Auto-generated method stub
		return videoMapper.insert(record);
	}

	@Override
	public int insertSelective(CmsVideo record) {
		// TODO Auto-generated method stub
		return videoMapper.insertSelective(record);
	}

	@Override
	public CmsVideo selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return videoMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CmsVideo record) {
		// TODO Auto-generated method stub
		return videoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CmsVideo record) {
		// TODO Auto-generated method stub
		return videoMapper.updateByPrimaryKey(record);
	}

	/**
	 * @param videoIdJson -
	 * 		{
	 *           "publicTime": "yyyy-MM-dd HH:mm:ss",
	 *           "expireTime": "yyyy-MM-dd HH:mm:ss",
	 *           ids:[{
	 *            	"id":"id值"
	 *           },{
	 *              "id":"id值"
	 *           }]
	 *      }
	 */
	@Override
	public int publishVideo(String videoIdJson) {
		
		JSONObject videoIdObj = JSONObject.fromObject(videoIdJson);
		String publicTime = videoIdObj.getString("publicTime");
		String expireTime = videoIdObj.getString("expireTime");
		String ids = videoIdObj.getString("ids");
		
		JSONArray videoIdArray = JSONArray.fromObject(ids);
		CmsVideo video = null;
		for(int i =0;i<videoIdArray.size();i++) {
			String videoIdStr = videoIdArray.get(i).toString();
			JSONObject videoObj =JSONObject.fromObject(videoIdStr);
			video = new CmsVideo();
			video.setId(videoObj.get("id").toString());
			video.setState(0); // 发布
			video.setPublicTime(publicTime);
			video.setExpireTime(expireTime);
			videoMapper.updateByPrimaryKeySelective(video);
		}
		return 1;
	}

	/**
	 * 批量转载
	 * @param videoIdJson -
	 * {
		 "platformId": "转载到的平台Id",
		 "hospitalId": "转载到的机构Id",
		 "columnId": "转载到的栏目Id",
		 "publicTime": "yyyy-MM-dd HH:mm:ss",
		 "expireTime": "yyyy-MM-dd HH:mm:ss",
		 "videoIds": [{
			"videoId": "转载的id值"
		 }, {
			"videoId": "转载的id值"
		 }]
	   }
	 * @param response
	 */
	@Override
	public int reshipmentVideo(Map<String, Object> parameter) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		String videoJson = parameter.get("videoJson").toString();
		String userId = parameter.get("userId").toString();

		JSONObject videoObj = JSONObject.fromObject(videoJson);

		String platformId = videoObj.get("platformId").toString();
		String hospitalId = videoObj.get("hospitalId").toString();
		String columnId = videoObj.get("columnId").toString();

		String publicTime = videoObj.getString("publicTime");
		String expireTime = videoObj.getString("expireTime");

		JSONArray reshipments = videoObj.getJSONArray("videoIds"); // 转载信息

		for (int i = 0; i < reshipments.size(); i++) {
			String videosStr = reshipments.get(i).toString();
			JSONObject videoIdObj = JSONObject.fromObject(videosStr);
			String videoId = videoIdObj.getString("videoId");
			// 查询出对应视频
			CmsVideo video = videoMapper.selectByPrimaryKey(videoId);
			if (video != null) {
				video.setId(UUIDUtil.getUUID());
				video.setFkColumnId(columnId);
				video.setHospitalId(hospitalId);
				video.setFkPlatformId(platformId);
				video.setVideoArrposid(2);
				video.setState(1);
				video.setPublicTime(publicTime);
				video.setExpireTime(expireTime);
				int reslut = videoMapper.insertSelective(video);
			}
		}
		return 1;
	}

	@Override
	public List<CmsVideoVo> findVideosPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return videoMapper.findVideosPage(parameter);
	}

	@Override
	public List<CmsVideo> findVideoColumnVideosPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return videoMapper.findVideoColumnVideosPage(parameter);
	}

	@Override
	public int deleteVideo(Map<String, Object> par) {
		String id= par.get("id").toString();
		int bannerFlag = Integer.parseInt(par.get("bannerFlag").toString());
		
		// 视频关联了banner 删除链接
		if(bannerFlag == 1) {
			// 查询 新闻 是否关联 了 banner 如果有  将 关联 删掉, 将banner关联状态改为未关联
			Map<String, Object> p = new HashMap();
			p.put("videoId", id);
			// 将此视频关联的banner状态改为 未关联 新闻, 一个新闻可能关联多个 banner
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
			param.put("videoId", id);
			// 删除视频banner 关联 
			bannerNewsMapper.deleteBannerVideo(param);
		}
		int reslut = videoMapper.deleteByPrimaryKey(id);
		return 0;
	}

	@Override
	public List<CmsVideo> findRemoteVideosPage(Map<String, Object> par) {
		// TODO Auto-generated method stub
		return videoMapper.findRemoteVideosPage(par);
	}

	@Override
	public CmsVideo findVideoRemote(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return videoMapper.findVideoRemote(parameter);
	}

}
