package com.dome.web.cmsvideo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.bean.CmsVideo;
import com.dome.bean.vo.CmsVideoVo;
import com.dome.bean.vo.TSUserVo;
import com.dome.util.ajax.AjaxUtil;
import com.dome.util.page.Page;
import com.dome.util.reverseback.ReverseBackData;
import com.dome.util.uuid.UUIDUtil;
import com.dome.web.cmsvideo.service.CmsVideoService;

/**
 * 视屏控制层
 * 
 * @author admin
 *
 */
@Controller
@RequestMapping("video")
public class CmsVideoController {

	@Resource(name = "videoService")
	private CmsVideoService videoService;
	
	/**
	 * 查询视频列表 
	 * @param currentPage - 开始页
	 * @param showCount	  - 每页显示条数
	 * @param hospitalId  - 医院id
	 * @param columnId	  - 栏目id
	 * @param videoName	  - 视频名称
	 * @param state	      - 状态 1: 保存草稿 0:发布
	 * @param startDate   - 开始时间
	 * @param endDate	  - 结束时间
	 * @param platformId  - 平台Id
	 * @param weightOrder - 按权重排序：不按此排序不传  1：升序,2:降序
	 * @param dataOrder	  - 时间排序：不按此排序不传      1:升序,2：降序
	 * @param sortOrder	  - 按前台顺序排序
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "findVideos")
	public void findVideos(Integer currentPage, Integer showCount, @RequestParam String hospitalId,
			@RequestParam String columnId, String videoName, Integer state,String startDate, String endDate,
			@RequestParam String platformId,Integer weightOrder,Integer dataOrder,int sortOrder,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			if(currentPage == null){
				currentPage = 1;
			}
			if(showCount == null){
				showCount = 20;
			}
			Page page=new Page();
		    page.setCurrentPage(currentPage);
		    page.setShowCount(showCount);
		    
			Map<String,Object> parameter = new HashMap();
			parameter.put("page", page);
			parameter.put("hospitalId", hospitalId);
			parameter.put("columnId", columnId);
			parameter.put("videoName", videoName);
			parameter.put("platformId", platformId); // 平台id
			parameter.put("state", state);
			parameter.put("startDate", startDate);
			parameter.put("endDate", endDate);
			
			parameter.put("weightOrder", weightOrder); // 按权重排序 1：升序,2:降序
			parameter.put("dataOrder", dataOrder); //  时间排序  1:升序,2：降序
			parameter.put("sortOrder", sortOrder); //  按前台 排序  0:不按此排序,2：按此排序
			List<CmsVideoVo> videoVos = videoService.findVideosPage(parameter);
			page.setList(videoVos);
			returnDate.setObj(page);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询视频列表!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}

	/**
	 * 添加视频
	 * 
	 * @param video
	 */
	@ResponseBody
	@RequestMapping(value = "addVideo")
	public void addVideo(CmsVideo video, HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Session session = SecurityUtils.getSubject().getSession();
			TSUserVo userVo = (TSUserVo) session.getAttribute("sysUser");
			String realname = userVo.getTsBaseUser().getRealname();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());
			String uuid = UUIDUtil.getUUID();
			video.setId(uuid);
			video.setState(0);
			video.setLearnNumber(0);
			video.setCreateTime(time);
			video.setUpdateTime(time);
			video.setCreateUser(userVo.getId());
			video.setCreateUserName(realname);
			video.setUpdateUser(userVo.getId());
			video.setUpdateUserName(realname);

			int reslut = videoService.insertSelective(video);
			if (reslut < 1) {
				returnDate.setFlag("error");
				returnDate.setMsg("添加视频错误!");
			} else {
				returnDate.setObj(uuid);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("添加视频异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}

	/**
	 * 查询视频
	 * 
	 * @param id
	 *            - 视频id
	 */
	@ResponseBody
	@RequestMapping(value = "findVideo")
	public void findVideo(@RequestParam String id, HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			CmsVideo video = videoService.selectByPrimaryKey(id);
			returnDate.setObj(video);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询视频异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}

	/**
	 * 修改视频
	 * 
	 * @param video
	 *            -
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "updateVideo")
	public void updateVideo(CmsVideo video, HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Session session = SecurityUtils.getSubject().getSession();
			TSUserVo userVo = (TSUserVo) session.getAttribute("sysUser");
			String realname = userVo.getTsBaseUser().getRealname();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());
			video.setUpdateTime(time);
			video.setUpdateUser(userVo.getId());
			video.setUpdateUserName(realname);
			int reslut = videoService.updateByPrimaryKeySelective(video);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("修改视频异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}

	/**
	 * 删除视频
	 * 
	 * @param id
	 *            - 视频Id
	 * @param bannerFlag
	 * 			  - 是否关联banner 0:未关联 1：已关联
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "deleteVideo")
	public void deleteVideo(@RequestParam String id,@RequestParam int bannerFlag,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Map<String,Object> par = new HashMap();
			par.put("id", id);
			par.put("bannerFlag", bannerFlag);
			int reslut = videoService.deleteVideo(par);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("删除视频异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}

	/**
	 * 批量发布视频
	 * 
	 * @param videoIdJson
	 *            - {
	 *            		"publicTime": "yyyy-MM-dd HH:mm:ss",
	 *            	    "expireTime": "yyyy-MM-dd HH:mm:ss",
	 *            		ids:[{
	 *            			"id":"id值"
	 *                  },{
	 *                  	"id":"id值"
	 *                  }]
	 *              }
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "publishVideo")
	public void publishVideo(@RequestParam String videoIdJson, HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			int reslut = videoService.publishVideo(videoIdJson);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("批量发布视频异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}

	/**
	 * 批量转载
	 * 
	 * @param videoIdJson 
	 *           - { 
	 *            		"platformId": "转载到的平台Id", 
	 *            		"hospitalId": "转载到的机构Id",
	 *            		"columnId": "转载到的栏目Id",
	 *            		"publicTime": "yyyy-MM-dd HH:mm:ss",
	 *            	    "expireTime": "yyyy-MM-dd HH:mm:ss", 
	 *                  "videoIds": [{
	 *                  	 "videoId":"转载的id值" 
	 *                 	 }, { 
	 *                 		"videoId": "转载的id值" 
	 *                   }] 
	 *              }
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "reshipmentVideo")
	public void reshipmentVideo(@RequestParam String videoJson, HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			Session session = SecurityUtils.getSubject().getSession();
			TSUserVo userVo = (TSUserVo) session.getAttribute("sysUser");

			Map<String, Object> parameter = new HashMap<>();
			parameter.put("videoJson", videoJson);
			parameter.put("userId", userVo.getId());
			int reslut = videoService.reshipmentVideo(parameter);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("转载异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}

	/**
	 * 修改视频 权重
	 * @param videoId - 视频id
	 * @param sort	  - 权重	
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "updateVideoSort")
	public void updateVideoSort(@RequestParam String videoId,int sort,HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			CmsVideo video = new CmsVideo();
			video.setId(videoId);
			video.setSort(sort);
			int reslut = videoService.updateByPrimaryKeySelective(video);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("修改视频权重异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	/**
	 * 查询视频类型栏目 的视频列表
	 * @param currentPage - 开始页
	 * @param showCount - 每页显示条数
	 * @param columnId	 - 栏目id
	 * @param videoName	 - 视频名称
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "findVideoColumnVideos")
	public void findVideoColumnVideos(Integer currentPage, Integer showCount,@RequestParam String columnId, String videoName, HttpServletResponse response) {
		ReverseBackData returnDate = new ReverseBackData();
		try {
			if(currentPage == null){
				currentPage = 1;
			}
			if(showCount == null){
				showCount = 10;
			}
			Page page=new Page();
		    page.setCurrentPage(currentPage);
		    page.setShowCount(showCount);
		    
			Map<String,Object> parameter = new HashMap();
			parameter.put("page", page);
			parameter.put("videoName", videoName);
			parameter.put("columnId", columnId);
			List<CmsVideo> videos = videoService.findVideoColumnVideosPage(parameter);
			returnDate.setObj(videos);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			returnDate.setFlag("error");
			returnDate.setMsg("查询视频类型栏目的视频列表异常!");
		}
		AjaxUtil.ajaxResponse(AjaxUtil.newGson().toJson(returnDate), response);
	}
	
	
}
