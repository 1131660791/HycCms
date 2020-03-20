package com.dome.web.cmsvideo.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.bean.CmsVideo;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.page.Page;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.web.cmsvideo.service.CmsVideoService;

/**
 * 远程连接 视频 控制层
 * @author admin
 *
 */
@Controller
@RequestMapping("videoRemote")
public class CmsVideoRemoteController {
	
	@Resource
	private CmsVideoService videoService;
	
	/**
	 * 远程调用 - 查询视频列表
	 * 
	 * @param platformId
	 *            - 平台Id
	 * @param hospitalId
	 *            - 医院id
	 * @param columnId
	 *            - 栏目Id - 首页新闻就不传
	 * @param videoArrposid
	 *            - 1：首页视频,2:栏目视频
	 */
	@ResponseBody
	@RequestMapping("findVideoListRemote")
	public void findVideoListRemote(Integer currentPage, Integer showCount, String hospitalId,
			@RequestParam String platformId, String columnId, int videoArrposid, HttpServletResponse response)
			throws IOException {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
			Map<String, Object> par = new HashMap();
			String time = sdf.format(new Date());
			if(currentPage == null){
				currentPage = 1;
			}
			if(showCount == null){
				showCount = 99999;
			}
			if(!StringUtils.hasText(hospitalId)) { // 如果医院id不存在
				hospitalId = "4028810267ddd9370167dddc6d1f0001"; // 就默认为 本平台最外层
			}
			Page page=new Page();
		    page.setCurrentPage(currentPage);
		    page.setShowCount(showCount);
		    
			par.put("columnId", columnId);
			par.put("hospitalId", hospitalId);
			par.put("platformId", platformId);
			par.put("videoArrposid", videoArrposid);
		    par.put("page", page);
			List<CmsVideo> videos = videoService.findRemoteVideosPage(par);
			page.setList(videos);
			returnDate.setObj(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询视频列表异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 观看视频
	 * @param videoId - 观看视频
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("watchVideoRemote")
	public void watchVideoRemote(@RequestParam String videoId, HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			// 查询视频
			CmsVideo video = videoService.selectByPrimaryKey(videoId);
			if(video != null) {
				Integer learnNumber = video.getLearnNumber();
				if(learnNumber == null || learnNumber.equals(null)) {
					learnNumber = 0;
				}
				learnNumber = learnNumber + 1;
				CmsVideo v = new CmsVideo();
				v.setId(videoId);
				v.setLearnNumber(learnNumber);
				videoService.updateByPrimaryKeySelective(v);
			}else {
				returnDate.setFlag("error");
				returnDate.setMsg("未发现该视频!");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("增加观看视频次数异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 查询视频
	 * @param videoId - 视频id
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("findVideoRemote")
	public void findVideoRemote(@RequestParam String videoId, HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String, Object> parameter = new HashMap();
			parameter.put("videoId", videoId);
			// 查询视频
			CmsVideo video = videoService.findVideoRemote(parameter);
			returnDate.setObj(video);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询新闻异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
}
